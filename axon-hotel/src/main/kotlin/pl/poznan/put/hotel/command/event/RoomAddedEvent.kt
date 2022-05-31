package pl.poznan.put.hotel.command.event

data class RoomAddedEvent(
    val roomNumber: Int,
    val roomDescription: String,
)