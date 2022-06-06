package pl.poznan.put.hotel.inventory.room.query.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class RoomEntity(
    @Id
    var roomId: UUID,
    var roomNumber: Int,
    var description: String,
    var addedToInventory: Boolean = false,
    var addedToBooking: Boolean,
)