package rebelsrescue.infra.client.swapi.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class SwapiStarShip(
    @field:JsonProperty("name")
    val name: String?,

    @field:JsonProperty("passengers")
    val passengers: String?,

    @field:JsonProperty("cargo_capacity")
    val cargoCapacity: String?
)