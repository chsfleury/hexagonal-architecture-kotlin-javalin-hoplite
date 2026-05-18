package rebelsrescue.infra.persistence.fleet

import org.jooq.DSLContext
import rebelsrescue.db.tables.records.FleetStarshipsRecord
import rebelsrescue.db.tables.references.FLEET_STARSHIPS
import rebelsrescue.domain.fleet.model.Fleet
import rebelsrescue.domain.fleet.model.StarShip
import rebelsrescue.domain.fleet.spi.Fleets
import java.math.BigDecimal
import java.util.UUID

class FleetJooqRepository(
    private val jooq: DSLContext
): Fleets {

    override fun getById(id: UUID): Fleet? {
        val fleetStarships = jooq.selectFrom(FLEET_STARSHIPS)
            .where(FLEET_STARSHIPS.FLEET_ID.eq(id.toString()))
            .fetch()
            .map(::toModel)

        return if (fleetStarships.isNotEmpty()) Fleet(id, fleetStarships) else null
    }

    override fun save(fleet: Fleet): Fleet {
        val records = fleet.starShips
            .map { toRecord(fleet.id, it) }

        jooq.insertQuery(FLEET_STARSHIPS)
            .apply {
                records.forEach(::addRecord)
            }
            .execute()

        return fleet
    }

    private fun toModel(record: FleetStarshipsRecord): StarShip = StarShip(
        record.name!!,
        record.passengersCapacity!!,
        BigDecimal.valueOf(record.cargoCapacity!!)
    )

    private fun toRecord(fleetId: UUID, starShip: StarShip) = FleetStarshipsRecord(
        fleetId.toString(),
        starShip.name,
        starShip.passengersCapacity,
        starShip.cargoCapacity.toLong()
    )

}