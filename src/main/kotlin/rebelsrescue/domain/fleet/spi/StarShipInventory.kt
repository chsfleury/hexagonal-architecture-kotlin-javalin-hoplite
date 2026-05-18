package rebelsrescue.domain.fleet.spi

import rebelsrescue.domain.fleet.model.StarShip

interface StarShipInventory {
    fun starShips(): List<StarShip>
}