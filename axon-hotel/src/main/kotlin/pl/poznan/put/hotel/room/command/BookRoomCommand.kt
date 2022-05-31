package pl.poznan.put.hotel.room.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import pl.poznan.put.hotel.room.command.model.RoomBooking

data class BookRoomCommand(
    @TargetAggregateIdentifier
    val roomNumber: Int,
    val roomBooking: RoomBooking,
)