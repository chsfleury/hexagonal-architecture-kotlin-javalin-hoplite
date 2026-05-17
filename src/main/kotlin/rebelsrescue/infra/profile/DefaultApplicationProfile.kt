package rebelsrescue.infra.profile

import org.jooq.DSLContext
import rebelsrescue.domain.fleet.FleetAssembler
import rebelsrescue.domain.fleet.api.AssembleAFleetUseCase
import rebelsrescue.domain.fleet.spi.Fleets
import rebelsrescue.domain.fleet.spi.StarShipInventory
import rebelsrescue.infra.api.controller.RescueFleetController
import rebelsrescue.infra.client.swapi.SwapiClient
import rebelsrescue.infra.configuration.Config
import rebelsrescue.infra.persistence.Database
import rebelsrescue.infra.persistence.fleet.FleetJooqRepository
import javax.sql.DataSource

class DefaultApplicationProfile(
    override val config: Config
): ApplicationProfile {
    override val dataSource: DataSource = Database.datasource(config.database)
    override val jooq: DSLContext = Database.jooq(dataSource, config.database)
    override val fleets: Fleets = FleetJooqRepository(jooq)
    override val starShipInventory: StarShipInventory = SwapiClient(config.swapi)
    override val assembleAFleetUseCase: AssembleAFleetUseCase = FleetAssembler(starShipInventory, fleets)
    override val rescueFleetController: RescueFleetController = RescueFleetController(assembleAFleetUseCase, fleets)


}
