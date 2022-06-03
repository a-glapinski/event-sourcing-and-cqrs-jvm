package pl.poznan.put.hotel.room.event.coreapi

import java.util.*

data class RoomCheckedOutEvent(
    val roomNumber: Int,
    val roomBookingId: UUID,
)