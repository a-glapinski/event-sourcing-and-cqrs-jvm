package pl.poznan.put.hotel.room.event

import pl.poznan.put.hotel.room.command.model.RoomBooking

// Room
data class RoomBookedEvent(
    val roomNumber: Int,
    val roomBooking: RoomBooking,
)