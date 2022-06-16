package pl.poznan.put.hotel.booking.room.dto

data class RoomRequest(
    val roomNumber: Int,
    val description: String,
)