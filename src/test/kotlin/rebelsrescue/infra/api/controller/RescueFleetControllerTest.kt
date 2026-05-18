package rebelsrescue.infra.api.controller

import org.assertj.core.api.Assertions.assertThat
import org.http4k.core.ContentType
import org.http4k.core.Method
import org.http4k.core.Status
import org.http4k.lens.contentType
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import rebelsrescue.domain.fleet.model.Fleet
import rebelsrescue.domain.fleet.model.StarShip
import java.math.BigDecimal

class RescueFleetControllerTest: AbstractControllerTest() {

    @Test
    fun should_assemble_a_rescue_fleet() {
        val post = request(Method.POST, "/rescueFleets")
            .contentType(ContentType.APPLICATION_JSON)
            .body("{ \"numberOfPassengers\" : 5 }")

        val response = http(post)
        assertThat(response.status).isEqualTo(Status.CREATED)
        JSONAssert.assertEquals("{}", response.body.text, false)
    }

    @Test
    fun should_return_a_fleet_given_an_id() {
        val fleet = Fleet(starShips = listOf(
            StarShip("Millennium Falcon", 6, BigDecimal("100000"))
        ))
        applicationProfile().fleets.save(fleet)
        val get = request(Method.GET, "/rescueFleets/${fleet.id}")
        val response = http(get)
        assertThat(response.status).isEqualTo(Status.OK)

        val expectedBody = """
            {
              "starships" : [ {
                "name" : "Millennium Falcon",
                "capacity" : 6,
                "passengersCapacity" : 6,
                "deprecations" : {
                  "capacity" : "passengersCapacity"
                }
              } ]
            }
        """.trimIndent()

        JSONAssert.assertEquals(expectedBody, response.body.text, false)
    }
}