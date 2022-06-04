package pl.poznan.put.hotel.booking.room.event.coreapi

import java.util.*

data class RoomCheckedInEvent(
    val roomNumber: Int,
    val roomBookingId: UUID,
)