package rebelsrescue.infra.api.controller

import io.javalin.http.Context
import io.javalin.http.HttpStatus
import io.javalin.http.bodyAsClass
import rebelsrescue.domain.fleet.api.AssembleAFleetUseCase
import rebelsrescue.domain.fleet.spi.Fleets
import rebelsrescue.infra.api.dto.RescueFleetRequest
import rebelsrescue.infra.api.dto.problem.ApiProblem
import rebelsrescue.infra.api.resource.FleetResource
import rebelsrescue.infra.utils.toUUID
import java.util.UUID

class RescueFleetController(
    private val assembleAFleet: AssembleAFleetUseCase,
    private val fleets: Fleets
) {

    fun assembleAFleet(ctx: Context) {
        val request = ctx.bodyAsClass<RescueFleetRequest>()
        val fleet = assembleAFleet(request.numberOfPassengers)
        ctx.status(HttpStatus.CREATED).json(FleetResource(fleet))
    }

    fun getFleet(ctx: Context) {
        val uuid = pathUUID(ctx) ?: return

        val fleet = fleets.getById(uuid)

        if (fleet == null) {
            val problem = ApiProblem(
                type = "/problem/fleet/not-found",
                title = "Fleet not found",
                status = HttpStatus.NOT_FOUND.code,
                detail = "The requested fleet with ID '$uuid' was not found.",
                instance = ctx.fullUrl()
            )
            ctx.status(HttpStatus.NOT_FOUND).json(problem)
        } else {
            ctx.status(HttpStatus.OK).json(FleetResource(fleet))
        }
    }

    private fun pathUUID(ctx: Context): UUID? {
        val id = ctx.pathParam("uuid").toUUID()
        if (id == null) {
            val problem = ApiProblem(
                type = "/problem/fleet/invalid-id",
                title = "Invalid fleet ID format",
                status = HttpStatus.BAD_REQUEST.code,
                detail = "The provided fleet ID is not a valid UUID format.",
                instance = ctx.fullUrl()
            )
            ctx.status(HttpStatus.BAD_REQUEST).json(problem)
        }
        return id
    }

}