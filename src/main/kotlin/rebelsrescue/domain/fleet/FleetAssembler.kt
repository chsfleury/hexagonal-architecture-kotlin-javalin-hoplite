package rebelsrescue.domain.fleet

import rebelsrescue.domain.fleet.api.AssembleAFleetUseCase
import rebelsrescue.domain.fleet.spi.Fleets
import rebelsrescue.domain.fleet.spi.StarShipInventory
import java.math.BigDecimal

class FleetAssembler(
    private val starShipsInventory: StarShipInventory,
    private val fleets: Fleets
): AssembleAFleetUseCase {

    companion object {
        val MINIMAL_CARGO_CAPACITY = BigDecimal("100000")
    }

    override fun forPassengers(numberOfPassengers: Int): Fleet {
        val starShips = getStarShipHavingPassengersCapacity()
        val rescueStarShips = selectStarShips(numberOfPassengers, starShips)
        return fleets.save(Fleet(starShips = rescueStarShips))
    }

    private fun selectStarShips(numberOfPassengers: Int, starShips: List<StarShip>): List<StarShip> {
        var remainingNumberOfPassengers = numberOfPassengers
        return starShips
            .filter { it.cargoCapacity >= MINIMAL_CARGO_CAPACITY }
            .takeWhile {
                val shouldTake = remainingNumberOfPassengers > 0
                remainingNumberOfPassengers -= it.passengersCapacity
                shouldTake
            }
    }

    private fun getStarShipHavingPassengersCapacity() = starShipsInventory
        .starShips().asSequence()
        .filter { it.passengersCapacity > 0 }
        .sortedBy(StarShip::passengersCapacity)
        .toList()
}