package pl.poznan.put.hotel.room.model.event

data class RoomAddedEvent(
    val roomNumber: Int,
    val roomDescription: String,
)