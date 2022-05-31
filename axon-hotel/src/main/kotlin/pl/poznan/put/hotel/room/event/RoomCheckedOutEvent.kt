package pl.poznan.put.hotel.room.event

import java.util.*

data class RoomCheckedOutEvent(
    val roomNumber: Int,
    val roomBookingId: UUID,
)