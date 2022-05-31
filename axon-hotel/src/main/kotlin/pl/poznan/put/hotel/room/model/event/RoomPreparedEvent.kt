package pl.poznan.put.hotel.room.model.event

import pl.poznan.put.hotel.room.model.RoomBooking

data class RoomPreparedEvent(
    val roomNumber: Int,
    val roomBooking: RoomBooking,
)