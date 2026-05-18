package rebelsrescue.infra.api.resource

import rebelsrescue.domain.fleet.model.Fleet
import java.util.UUID

data class FleetResource(
    val id: UUID,
    val starships: List<StarShipResource>
) {
    constructor(fleet: Fleet) : this(
        fleet.id,
        fleet.starShips.map(::StarShipResource)
    )
}