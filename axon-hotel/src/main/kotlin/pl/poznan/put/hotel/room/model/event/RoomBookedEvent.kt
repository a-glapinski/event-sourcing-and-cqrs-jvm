package pl.poznan.put.hotel.room.model.event

import pl.poznan.put.hotel.room.model.RoomBooking

// Room
data class RoomBookedEvent(
    val roomNumber: Int,
    val roomBooking: RoomBooking,
)