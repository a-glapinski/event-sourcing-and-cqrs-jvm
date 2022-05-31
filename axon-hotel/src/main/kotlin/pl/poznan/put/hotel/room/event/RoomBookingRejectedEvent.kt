package pl.poznan.put.hotel.room.event

import pl.poznan.put.hotel.room.command.model.RoomBooking

data class RoomBookingRejectedEvent(
    val roomNumber: Int,
    val roomBooking: RoomBooking,
    val reason: String,
)