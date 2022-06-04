package pl.poznan.put.hotel.booking.room.command.dto

data class RoomRequest(
    val roomNumber: Int,
    val description: String,
)