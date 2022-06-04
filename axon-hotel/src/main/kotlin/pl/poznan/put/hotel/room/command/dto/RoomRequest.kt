package pl.poznan.put.hotel.room.command.dto

data class RoomRequest(
    val roomNumber: Int,
    val description: String,
)