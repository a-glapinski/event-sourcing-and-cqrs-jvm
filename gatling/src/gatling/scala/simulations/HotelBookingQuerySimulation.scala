package simulations

import common.Constants
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class HotelBookingQuerySimulation extends Simulation {
  val httpProtocol = http.baseUrl(Constants.HotelBookingQueryGatewayBaseUrl)

  val getAccount = exec(
    http("Get account")
      .get("/accounts/d3b8915a-2b18-4d1d-b92b-19f24659bbd2")
      .check(status is 200)
  )

  val getRoomAvailabilityForAccount = exec(
    http("Get room availability for account")
      .get("/rooms/123/account/d3b8915a-2b18-4d1d-b92b-19f24659bbd2/availability")
      .check(status is 200)
  )

  val getAllPaymentsForAccount = exec(
    http("Get payments for account")
      .get("/payments/account/d3b8915a-2b18-4d1d-b92b-19f24659bbd2")
      .check(status is 200)
  )

  val scn = scenario("Getting basic information")
    .exec(getAccount, getRoomAvailabilityForAccount, getAllPaymentsForAccount)

  setUp(
    scn
      .inject(
        incrementUsersPerSec(10)
          .times(15)
          .eachLevelLasting(10.seconds)
          .separatedByRampsLasting(10.seconds),
        nothingFor(90.seconds),
        //        constantUsersPerSec(125).during(5.minutes),
      )
      //      .throttle(reachRps(375).in(0.seconds), holdFor(15.minutes))
      .protocols(httpProtocol)
  )
}