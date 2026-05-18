package rebelsrescue.infra

import io.javalin.Javalin
import io.javalin.config.JavalinConfig
import rebelsrescue.infra.configuration.Config
import rebelsrescue.infra.profile.ApplicationProfile
import rebelsrescue.infra.profile.DefaultApplicationProfile

class StarwarsRebelsRescueApplication(
    val applicationProfile: ApplicationProfile
) {
    private var javalin: Javalin? = null

    fun start(): Javalin {
        System.setProperty("org.jooq.no-logo", "true")
        System.setProperty("org.jooq.no-tips", "true")

        javalin = Javalin.create { config: JavalinConfig ->
            config.routes
                .get("/rescueFleets/{uuid}") { ctx -> applicationProfile.rescueFleetController.getFleet(ctx) }
                .post("/rescueFleets") { ctx -> applicationProfile.rescueFleetController.assembleAFleet(ctx) }
        }.start(applicationProfile.config.server.port)
        return javalin!!
    }

    fun stop() {
        javalin?.stop()
    }

    fun port() = javalin?.port() ?: applicationProfile.config.server.port

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val propertySource = args.firstOrNull() ?: "/application.yml"
            StarwarsRebelsRescueApplication(
                DefaultApplicationProfile(
                    Config(propertySource)
                )
            ).start()
        }
    }
}