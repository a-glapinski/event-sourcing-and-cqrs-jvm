package pl.poznan.put.hotel.room.event.coreapi

import pl.poznan.put.hotel.room.command.model.RoomBooking

data class RoomPreparedEvent(
    val roomNumber: Int,
    val roomBooking: RoomBooking,
)