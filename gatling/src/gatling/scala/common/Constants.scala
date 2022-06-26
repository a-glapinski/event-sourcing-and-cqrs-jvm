package common

object Constants {
  private val GatewayBaseUrl = "http://localhost:8080"

  val HotelBookingQueryBaseUrl = s"$GatewayBaseUrl/api/hotel-booking/query"
  val HotelBookingCommandBaseUrl = s"$GatewayBaseUrl/api/hotel-booking/command"
}
