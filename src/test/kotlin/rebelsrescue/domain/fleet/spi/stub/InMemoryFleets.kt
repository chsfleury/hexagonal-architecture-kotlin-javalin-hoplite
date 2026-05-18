package rebelsrescue.domain.fleet.spi.stub

import rebelsrescue.domain.fleet.model.Fleet
import rebelsrescue.domain.fleet.spi.Fleets
import java.util.UUID

data class InMemoryFleets(
    private val fleets: MutableMap<UUID, Fleet> = mutableMapOf()
): Fleets {
    override fun getById(id: UUID): Fleet? = fleets[id]

    override fun save(fleet: Fleet): Fleet {
        fleets[fleet.id] = fleet
        return fleet
    }

}
