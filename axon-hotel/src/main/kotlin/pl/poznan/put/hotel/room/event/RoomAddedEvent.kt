package pl.poznan.put.hotel.room.event

data class RoomAddedEvent(
    val roomNumber: Int,
    val roomDescription: String,
)