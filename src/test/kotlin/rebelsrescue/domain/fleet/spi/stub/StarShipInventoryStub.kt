package rebelsrescue.domain.fleet.spi.stub

import rebelsrescue.domain.fleet.StarShip
import rebelsrescue.domain.fleet.spi.StarShipInventory
import java.math.BigDecimal

class StarShipInventoryStub(
    private val starShips: List<StarShip> = DEFAULT_STARSHIPS
): StarShipInventory {

    override fun starShips(): List<StarShip> = starShips

    companion object {
        private val DEFAULT_STARSHIPS = listOf(
            StarShip("X-Wing", 0, BigDecimal("100")),
            StarShip("Millennium Falcon", 6, BigDecimal("100000")),
            StarShip("Rebel transport", 90, BigDecimal("80000")),
            StarShip("Mon Calamari Star Cruisers", 1200, BigDecimal("200000")),
            StarShip("CR90 corvette", 600, BigDecimal("300000")),
            StarShip("EF76 Nebulon-B escort frigate", 800, BigDecimal("350000"))
        )
    }

}