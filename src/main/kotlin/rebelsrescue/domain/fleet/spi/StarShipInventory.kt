package rebelsrescue.domain.fleet.spi

import rebelsrescue.domain.fleet.StarShip

interface StarShipInventory {
    fun starShips(): List<StarShip>
}