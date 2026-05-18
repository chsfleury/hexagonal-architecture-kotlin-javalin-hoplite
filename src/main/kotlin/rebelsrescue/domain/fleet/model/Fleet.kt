package rebelsrescue.domain.fleet.model

import java.util.UUID

data class Fleet(
    val id: UUID = UUID.randomUUID(),
    val starShips: List<StarShip> = emptyList()
)
