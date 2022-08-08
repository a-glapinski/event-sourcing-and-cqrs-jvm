package simulations

import common.Constants
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import util.Feeders

import scala.concurrent.duration._

class HotelBookingCommandSimulation extends Simulation {
  val httpProtocol = http.baseUrl(Constants.HotelBookingCommandGatewayBaseUrl)

  val registerAccount = exec(
    http("Register account")
      .post("/accounts")
      .body(StringBody(
        """
        {
          "accountId": "#{uuid}",
          "password": "asd123",
          "username": "user#{positiveInt}"
        }
      """))
      .asJson
      .check(status is 200)
  )

  val addRoom = exec(
    http("Add room")
      .post("/rooms")
      .body(StringBody(
        """
          {
            "description": "Great Room",
            "roomNumber": #{positiveInt}
          }
        """))
      .asJson
      .check(status is 200)
  )

  val bookRoom = exec(
    http("Book room")
      .post("/rooms/#{positiveInt}/booked")
      .body(StringBody(
        """
          {
            "roomBookingId": "#{uuid}",
            "accountId": "#{uuid}",
            "endDate": "1960-08-17T03:39:13.668Z",
            "startDate": "2003-09-14T00:29:48.128Z"
          }
        """))
      .asJson
      .check(status is 200)
  )

  val scn = scenario("Hotel booking")
    .feed(Feeders.uuidFeeder).feed(Feeders.positiveIntFeeder)
    .exec(registerAccount, addRoom, bookRoom)

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
