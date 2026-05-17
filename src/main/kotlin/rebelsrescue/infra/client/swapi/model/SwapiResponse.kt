package rebelsrescue.infra.client.swapi.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class SwapiResponse(

    @field:JsonProperty("next")
    val next: String?,

    @field:JsonProperty("results")
    val results: List<SwapiStarShip>?

)