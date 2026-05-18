package rebelsrescue.domain.fleet.model

import java.math.BigDecimal

data class StarShip(
    val name: String,
    val passengersCapacity: Int,
    val cargoCapacity: BigDecimal
)
