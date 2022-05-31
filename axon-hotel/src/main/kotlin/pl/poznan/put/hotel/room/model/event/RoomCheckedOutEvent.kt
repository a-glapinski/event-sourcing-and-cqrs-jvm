package pl.poznan.put.hotel.room.model.event

import java.util.*

data class RoomCheckedOutEvent(
    val roomNumber: Int,
    val roomBookingId: UUID,
)