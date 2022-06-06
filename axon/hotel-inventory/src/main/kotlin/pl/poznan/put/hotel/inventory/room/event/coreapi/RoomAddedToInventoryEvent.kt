package pl.poznan.put.hotel.inventory.room.event.coreapi

import java.util.*

data class RoomAddedToInventoryEvent(
    val roomId: UUID,
    val roomNumber: Int,
    val roomDescription: String,
)