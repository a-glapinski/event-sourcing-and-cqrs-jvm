package pl.poznan.put.hotel.room.query.dto

data class RoomCheckoutScheduleDto(
    val roomNumber: Int,
    val bookings: List<BookingDto>,
)