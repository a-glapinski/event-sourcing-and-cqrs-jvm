package pl.poznan.put.hotel.booking.room.event

import java.util.*

data class RoomCheckedInEvent(
    val roomNumber: Int,
    val roomBookingId: UUID,
)