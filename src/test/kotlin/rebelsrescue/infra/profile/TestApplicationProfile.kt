package rebelsrescue.infra.profile

import org.jooq.DSLContext
import rebelsrescue.domain.fleet.service.FleetAssembler
import rebelsrescue.domain.fleet.api.AssembleAFleetUseCase
import rebelsrescue.domain.fleet.spi.Fleets
import rebelsrescue.domain.fleet.spi.StarShipInventory
import rebelsrescue.domain.fleet.spi.stub.StarShipInventoryStub
import rebelsrescue.infra.api.controller.RescueFleetController
import rebelsrescue.infra.configuration.Config
import rebelsrescue.infra.persistence.Database
import rebelsrescue.infra.persistence.fleet.FleetJooqRepository
import javax.sql.DataSource

class TestApplicationProfile(
    override val config: Config
): ApplicationProfile {
    override val dataSource: DataSource = Database.datasource(config.database)
    override val jooq: DSLContext = Database.jooq(dataSource, config.database)
    override val fleets: Fleets = FleetJooqRepository(jooq)
    override val starShipInventory: StarShipInventory = StarShipInventoryStub()
    override val assembleAFleetUseCase: AssembleAFleetUseCase = FleetAssembler(starShipInventory, fleets)
    override val rescueFleetController: RescueFleetController = RescueFleetController(assembleAFleetUseCase, fleets)
}
