package rebelsrescue.infra.profile

import org.jooq.DSLContext
import rebelsrescue.domain.fleet.api.AssembleAFleetUseCase
import rebelsrescue.domain.fleet.spi.Fleets
import rebelsrescue.domain.fleet.spi.StarShipInventory
import rebelsrescue.infra.api.controller.RescueFleetController
import rebelsrescue.infra.configuration.Config
import javax.sql.DataSource

interface ApplicationProfile {
    // CONFIG
    val config: Config

    // DATABASE
    val dataSource: DataSource
    val jooq: DSLContext

    // SPI
    val fleets: Fleets
    val starShipInventory: StarShipInventory

    // USE CASES
    val assembleAFleetUseCase: AssembleAFleetUseCase

    // CONTROLLERS
    val rescueFleetController: RescueFleetController
}