package rebelsrescue.domain.fleet

import java.math.BigDecimal

data class StarShip(
    val name: String,
    val passengersCapacity: Int,
    val cargoCapacity: BigDecimal
)
