package pl.poznan.put.hotel.command.event

import pl.poznan.put.hotel.command.model.RoomBooking

data class RoomBookingRejectedEvent(
    val roomNumber: Int,
    val roomBooking: RoomBooking,
    val reason: String,
)