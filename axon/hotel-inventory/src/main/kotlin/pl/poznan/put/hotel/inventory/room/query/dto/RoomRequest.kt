package pl.poznan.put.hotel.inventory.room.query.dto

data class RoomRequest(
    val roomNumber: Int,
    val description: String,
)