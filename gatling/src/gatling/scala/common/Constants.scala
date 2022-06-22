package common

object Constants {
  private val GatewayBaseUrl = "http://localhost:8080"

  val HotelBookingQueryBaseUrl = s"$GatewayBaseUrl/api/hotel-booking/query"
  val HotelBookingQueryCommandBaseUrl = s"$GatewayBaseUrl/api/hotel-booking/command"

  val AcceptHeaders = "application/json,text/html,application/xhtml+xml,application/xml,*/*"
}
