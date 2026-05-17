package rebelsrescue.infra.client.swapi

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.http4k.client.JavaHttpClient
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import rebelsrescue.domain.fleet.StarShip
import rebelsrescue.domain.fleet.spi.StarShipInventory
import rebelsrescue.infra.client.swapi.model.SwapiResponse
import rebelsrescue.infra.client.swapi.model.SwapiStarShip
import rebelsrescue.infra.configuration.SwapiConfig
import rebelsrescue.infra.utils.JSON
import java.math.BigDecimal

class SwapiClient(
    private val swapiConfig: SwapiConfig,
    private val http: HttpHandler = JavaHttpClient(),
    private val objectMapper: ObjectMapper = JSON,
    private val invalidCapacitiesValues: List<String> = listOf("n/a", "unknown")
): StarShipInventory {

    override fun starShips(): List<StarShip> {
        val starShips = mutableListOf<StarShip>()
        var nextPageUrl: String? = "/api/starships"
        while (nextPageUrl != null) {
            val swapiResponse = getStarShipsFromSwapi(nextPageUrl)
            starShips.addAll(convertSwapiResponseToStarShips(swapiResponse))
            nextPageUrl = swapiResponse.next
        }
        return starShips
    }

    private fun convertSwapiResponseToStarShips(swapiResponse: SwapiResponse): List<StarShip> = (swapiResponse.results?: emptyList()).asSequence()
        .filter(::hasValidPassengersValue)
        .mapNotNull(::toStarShip)
        .toList()

    private fun toStarShip(swapiStarShip: SwapiStarShip): StarShip? = StarShip(
        swapiStarShip.name ?: return null,
        swapiStarShip.passengers?.replace(",", "")?.toIntOrNull() ?: return null,
        swapiStarShip.cargoCapacity?.let(::BigDecimal) ?: return null
    )

    private fun hasValidPassengersValue(swapiStarShip: SwapiStarShip): Boolean = swapiStarShip.passengers !in invalidCapacitiesValues
            && swapiStarShip.cargoCapacity !in invalidCapacitiesValues

    private fun getStarShipsFromSwapi(url: String): SwapiResponse {
        val request = Request(Method.GET, uri(url))
        val response = http(request)
        return objectMapper.readValue<SwapiResponse>(response.body.text)
    }

    private fun uri(url: String): String = if (url.startsWith("http")) url else swapiConfig.baseUrl + url

}