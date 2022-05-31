package pl.poznan.put.hotel.command.event

import pl.poznan.put.hotel.command.model.RoomBooking

// Room
data class RoomBookedEvent(
    val roomNumber: Int,
    val roomBooking: RoomBooking,
)