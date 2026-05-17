package rebelsrescue.infra.api.controller

import org.http4k.client.JavaHttpClient
import org.http4k.core.Method
import org.http4k.core.Request
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import rebelsrescue.infra.StarwarsRebelsRescueApplication
import rebelsrescue.infra.configuration.Config
import rebelsrescue.infra.persistence.Database
import rebelsrescue.infra.profile.ApplicationProfile
import rebelsrescue.infra.profile.TestApplicationProfile

abstract class AbstractControllerTest {

    protected val http = JavaHttpClient()

    companion object {

        protected lateinit var baseUrl: String
        protected lateinit var app: StarwarsRebelsRescueApplication

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            val testConfig = Config("/application-test.yml")
            val testProfile = TestApplicationProfile(testConfig)
            Database.migrate(testProfile.dataSource)
            app = StarwarsRebelsRescueApplication(testProfile)
            app.start()
            baseUrl = "http://localhost:${app.port()}"
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            app.stop()
        }
    }

    protected fun request(method: Method, path: String): Request = Request(method, baseUrl + path)

    protected fun applicationProfile(): ApplicationProfile = app.applicationProfile

}