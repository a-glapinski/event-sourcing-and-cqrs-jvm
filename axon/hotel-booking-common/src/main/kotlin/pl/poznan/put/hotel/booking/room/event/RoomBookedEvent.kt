package pl.poznan.put.hotel.booking.room.event

import pl.poznan.put.hotel.booking.room.valueobject.RoomBooking

data class RoomBookedEvent(
    val roomNumber: Int,
    val roomBooking: RoomBooking,
)