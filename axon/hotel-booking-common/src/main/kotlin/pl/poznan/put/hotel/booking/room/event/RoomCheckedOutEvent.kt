package pl.poznan.put.hotel.booking.room.event

import java.util.*

data class RoomCheckedOutEvent(
    val roomNumber: Int,
    val roomBookingId: UUID,
)