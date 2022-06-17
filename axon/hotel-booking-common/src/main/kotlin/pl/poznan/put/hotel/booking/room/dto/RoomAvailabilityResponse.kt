package pl.poznan.put.hotel.booking.room.dto

data class RoomAvailabilityResponse(
    val roomNumber: Int,
    val roomDescription: String,
    val roomStatus: RoomStatusDto,
    val bookings: List<BookingDto>,
    val myFailedBookings: List<FailedBookingDto>,
)