package pl.poznan.put.hotel.inventory.room.query.dto

import pl.poznan.put.hotel.inventory.room.query.model.RoomEntity
import java.util.*

data class RoomOverviewResponse(
    val id: UUID,
    val roomNumber: Int,
    val description: String,
    val addedToInventory: Boolean,
    val addedToBooking: Boolean,
) {
    constructor(roomEntity: RoomEntity) : this(
        id = roomEntity.roomId,
        roomNumber = roomEntity.roomNumber,
        description = roomEntity.description,
        addedToInventory = roomEntity.addedToInventory,
        addedToBooking = roomEntity.addedToBooking,
    )
}