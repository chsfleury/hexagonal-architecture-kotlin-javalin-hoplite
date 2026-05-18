package rebelsrescue.infra.api.resource

import rebelsrescue.domain.fleet.model.StarShip

data class StarShipResource(
    val name: String,
    val capacity: Int,
    val passengersCapacity: Int,
    val deprecations: Map<String, String>
) {
    constructor(starShip: StarShip) : this(
        starShip.name,
        starShip.passengersCapacity,
        starShip.passengersCapacity,
        mapOf("capacity" to "passengersCapacity")
    )
}