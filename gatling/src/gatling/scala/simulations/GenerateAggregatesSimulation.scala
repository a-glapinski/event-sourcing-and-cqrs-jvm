package simulations

import common.Constants
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class GenerateAggregatesSimulation extends Simulation {
  val httpProtocol = http.baseUrl(Constants.HotelBookingCommandGatewayBaseUrl)

  val registerAccount = exec(
    http("Register account")
      .post("/accounts")
      .body(StringBody(
        """
        {
          "accountId": "d3b8915a-2b18-4d1d-b92b-19f24659bbd2",
          "password": "asd123",
          "username": "user123"
        }
      """)).asJson
      .check(status is 200)
  )

  val addRoom = exec(
    http("Add room")
      .post("/rooms")
      .body(StringBody(
        """
          {
            "description": "Great Room",
            "roomNumber": 123
          }
        """)).asJson
      .check(status is 200)
  )

  val bookRoom = exec(
    http("Book room")
      .post("/rooms/123/booked")
      .body(StringBody(
        """
          {
            "roomBookingId": "a3a18a79-067d-4a24-8a51-4a637333a22b",
            "accountId": "d3b8915a-2b18-4d1d-b92b-19f24659bbd2",
            "endDate": "1960-08-17T03:39:13.668Z",
            "startDate": "2003-09-14T00:29:48.128Z"
          }
        """)).asJson
      .check(status is 200)
  )

  val issuePayment = exec(
    http("Issue payment")
      .post("/payments")
      .body(StringBody(
        """
        {
          "paymentId": "c1b4f5fc-b8f4-48c1-b717-ef2c109b5fd6",
          "accountId": "d3b8915a-2b18-4d1d-b92b-19f24659bbd2",
          "totalAmount": 100.0
        }
      """)).asJson
      .check(status is 200)
  )

  val scn = scenario("Generate aggregates")
    .exec(registerAccount, addRoom, bookRoom, issuePayment)

  setUp(
    scn
      .inject(atOnceUsers(1))
      .protocols(httpProtocol)
  )
}
