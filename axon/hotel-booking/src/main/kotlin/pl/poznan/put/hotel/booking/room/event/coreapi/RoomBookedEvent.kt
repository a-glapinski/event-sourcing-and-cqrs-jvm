package pl.poznan.put.hotel.booking.room.event.coreapi

import pl.poznan.put.hotel.booking.room.command.model.RoomBooking

// Room
data class RoomBookedEvent(
    val roomNumber: Int,
    val roomBooking: RoomBooking,
)