package simulations

import common.Constants
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import util.Feeders

import scala.concurrent.duration._

class HotelBookingCommandSimulation extends Simulation {
  val httpProtocol = http
    .baseUrl(Constants.HotelBookingCommandBaseUrl)

  val addRoomRequest = StringBody(
    """
      {
        "description": "Great Room",
        "roomNumber": #{positiveInt}
      }
    """)
  val addRoom = exec(
    http("Add room")
      .post("/rooms")
      .body(addRoomRequest).asJson
      .check(status is 200)
  )

  val scn = scenario("Rooms")
    .feed(Feeders.positiveIntFeeder)
    .exec(addRoom)

  //  setUp(
  //    scn
  //      .inject(
  //        incrementUsersPerSec(10.0)
  //          .times(20)
  //          .eachLevelLasting(10)
  //          .separatedByRampsLasting(10)
  //          .startingFrom(100)
  //      )
  //      .protocols(httpProtocol)
  //  )

  setUp(scn.inject(constantUsersPerSec(300).during(2.minutes)))
    .throttle(
      reachRps(250).in(10),
      holdFor(2.minute)
    )
    .protocols(httpProtocol)
}
