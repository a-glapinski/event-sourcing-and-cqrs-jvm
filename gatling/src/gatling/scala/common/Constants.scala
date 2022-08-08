package common

object Constants {
  private val GatewayBaseUrl = "http://localhost:8080"

  val HotelBookingQueryGatewayBaseUrl = s"$GatewayBaseUrl/api/hotel-booking/query"
  val HotelBookingCommandGatewayBaseUrl = s"$GatewayBaseUrl/api/hotel-booking/command"
}
