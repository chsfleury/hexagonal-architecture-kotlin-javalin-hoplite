package rebelsrescue.domain.fleet.api

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Condition
import org.junit.jupiter.api.Test
import rebelsrescue.domain.fleet.Fleet
import rebelsrescue.domain.fleet.FleetAssembler
import rebelsrescue.domain.fleet.FleetAssembler.Companion.MINIMAL_CARGO_CAPACITY
import rebelsrescue.domain.fleet.StarShip
import rebelsrescue.domain.fleet.spi.Fleets
import rebelsrescue.domain.fleet.spi.StarShipInventory
import rebelsrescue.domain.fleet.spi.stub.InMemoryFleets
import rebelsrescue.domain.fleet.spi.stub.StarShipInventoryStub
import java.math.BigDecimal
import java.util.function.Predicate

class AssembleAFleetUseCaseTest {

    @Test
    fun should_assemble_a_fleet_for_1050_passengers() {
        //Given
        val starShips = listOf<StarShip>(
            StarShip("no-passenger-ship", 0, BigDecimal.ZERO),
            StarShip("xs", 10, BigDecimal("1000")),
            StarShip("s", 50, BigDecimal("50000")),
            StarShip("m", 200, BigDecimal("70000")),
            StarShip("l", 800, BigDecimal("150000")),
            StarShip("xl", 2000, BigDecimal("500000"))
        )
        val numberOfPassengers = 1050

        val starShipsInventory: StarShipInventory = StarShipInventoryStub(starShips)
        val fleets: Fleets = InMemoryFleets()
        val assembleAFleet: AssembleAFleetUseCase = FleetAssembler(starShipsInventory, fleets)

        //When
        val fleet: Fleet = assembleAFleet.forPassengers(numberOfPassengers)

        //Then
        println(fleet)
        assertThat(fleet.starShips)
            .has(enoughCapacityForThePassengers(numberOfPassengers))
            .allMatch(hasPassengersCapacity())
            .allMatch(hasEnoughCargoCapacity(), "hasEnoughCargoCapacity")

        assertThat(fleets.getById(fleet.id)).isEqualTo(fleet)
    }

    private fun hasPassengersCapacity(): Predicate<in StarShip> {
        return Predicate { starShip: StarShip -> starShip.passengersCapacity > 0 }
    }

    private fun hasEnoughCargoCapacity(): Predicate<in StarShip> {
        return Predicate { starShip: StarShip -> starShip.cargoCapacity > MINIMAL_CARGO_CAPACITY }
    }

    private fun enoughCapacityForThePassengers(numberOfPassengers: Int): Condition<in MutableList<out StarShip>> {
        return Condition(
            { starShips: MutableList<out StarShip> ->
                starShips.stream()
                    .map(StarShip::passengersCapacity)
                    .reduce(0, Integer::sum) >= numberOfPassengers
            },
            "passengersCapacity check"
        )
    }

}