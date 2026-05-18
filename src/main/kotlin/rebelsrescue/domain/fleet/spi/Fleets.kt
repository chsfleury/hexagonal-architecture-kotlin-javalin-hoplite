package rebelsrescue.domain.fleet.spi

import rebelsrescue.domain.fleet.model.Fleet
import java.util.UUID

interface Fleets {
    fun getById(id: UUID): Fleet?

    fun save(fleet: Fleet): Fleet
}