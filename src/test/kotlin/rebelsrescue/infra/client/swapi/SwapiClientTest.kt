package rebelsrescue.infra.client.swapi

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import rebelsrescue.domain.fleet.model.StarShip
import rebelsrescue.infra.configuration.SwapiConfig
import rebelsrescue.infra.utils.getFileContent
import java.math.BigDecimal

@WireMockTest(httpPort = 8082)
class SwapiClientTest {

    private val tested: SwapiClient = SwapiClient(
        SwapiConfig("http://localhost:8082")
    )

    @Test
    fun should_return_the_starship_inventory(wmRuntimeInfo: WireMockRuntimeInfo) {

        // Given
        val wiremock: WireMock = wmRuntimeInfo.wireMock
        val swapiPage1 = getFileContent("payloads/swapi-page1.json")
        val swapiPage2 = getFileContent("payloads/swapi-page2.json")

        wiremock.register(
            get("/api/starships").willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(swapiPage1)
            )
        )

        wiremock.register(
            get("/api/starships/?page=2").willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(swapiPage2)
            )
        )

        // When
        val starships = tested.starShips()

        // Then
        assertThat(starships)
            .hasSize(3)
            .containsExactly(
                StarShip("CR90 corvette", 600, BigDecimal("3000000")),
                StarShip("Slave 1", 6, BigDecimal("70000")),
                StarShip("Death Star", 843342, BigDecimal("1000000000000"))
            )
    }

}