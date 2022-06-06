package pl.poznan.put.hotel.inventory.room.event.coreapi

import java.util.*

data class RoomAddedToBookingSystemEvent(
    val roomId: UUID,
)