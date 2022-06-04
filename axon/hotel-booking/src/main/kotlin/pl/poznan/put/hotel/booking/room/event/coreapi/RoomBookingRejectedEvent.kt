package pl.poznan.put.hotel.booking.room.event.coreapi

import pl.poznan.put.hotel.booking.room.command.model.RoomBooking

data class RoomBookingRejectedEvent(
    val roomNumber: Int,
    val roomBooking: RoomBooking,
    val reason: String,
)