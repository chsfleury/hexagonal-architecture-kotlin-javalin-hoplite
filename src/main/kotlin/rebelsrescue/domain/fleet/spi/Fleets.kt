package rebelsrescue.domain.fleet.spi

import rebelsrescue.domain.fleet.Fleet
import java.util.UUID

interface Fleets {
    fun getById(id: UUID): Fleet?

    fun save(fleet: Fleet): Fleet
}