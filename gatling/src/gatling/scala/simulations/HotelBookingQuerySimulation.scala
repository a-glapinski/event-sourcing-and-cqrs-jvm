package simulations

import common.Constants
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class HotelBookingQuerySimulation extends Simulation {
  val httpProtocol = http
    .baseUrl(Constants.HotelBookingQueryBaseUrl)

  val getAllAccounts = exec(
    http("Get all accounts")
      .get("/accounts")
      .check(status is 200)
  )

  val scn = scenario("Accounts").exec(getAllAccounts)

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
}