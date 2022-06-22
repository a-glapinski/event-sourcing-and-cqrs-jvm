package simulations

import common.Constants
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class HotelBookingQuerySimulation extends Simulation {
  val httpProtocol = http
    .baseUrl(Constants.HotelBookingQueryBaseUrl)
    .acceptHeader(Constants.AcceptHeaders)

  val scn = scenario("Get account")
    .exec(
      http("request_1")
        .get("/accounts").check(status is 200)
    )

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
}