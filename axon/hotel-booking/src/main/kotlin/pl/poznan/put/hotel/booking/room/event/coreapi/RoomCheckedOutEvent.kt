package pl.poznan.put.hotel.booking.room.event.coreapi

import java.util.*

data class RoomCheckedOutEvent(
    val roomNumber: Int,
    val roomBookingId: UUID,
)