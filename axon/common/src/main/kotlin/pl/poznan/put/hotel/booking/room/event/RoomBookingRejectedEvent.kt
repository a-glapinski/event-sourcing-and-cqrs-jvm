package pl.poznan.put.hotel.booking.room.event

import pl.poznan.put.hotel.booking.room.valueobject.RoomBooking

data class RoomBookingRejectedEvent(
    val roomNumber: Int,
    val roomBooking: RoomBooking,
    val reason: String,
)