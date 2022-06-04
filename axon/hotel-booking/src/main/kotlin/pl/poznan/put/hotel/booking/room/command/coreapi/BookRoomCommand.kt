package pl.poznan.put.hotel.booking.room.command.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier
import pl.poznan.put.hotel.booking.room.command.model.RoomBooking

data class BookRoomCommand(
    @TargetAggregateIdentifier
    val roomNumber: Int,
    val roomBooking: RoomBooking,
)