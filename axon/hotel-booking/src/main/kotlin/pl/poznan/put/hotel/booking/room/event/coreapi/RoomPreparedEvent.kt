package pl.poznan.put.hotel.booking.room.event.coreapi

import pl.poznan.put.hotel.booking.room.command.model.RoomBooking

data class RoomPreparedEvent(
    val roomNumber: Int,
    val roomBooking: RoomBooking,
)