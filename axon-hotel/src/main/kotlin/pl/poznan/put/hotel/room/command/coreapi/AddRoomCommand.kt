package pl.poznan.put.hotel.room.command.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class AddRoomCommand(
    @TargetAggregateIdentifier
    val roomNumber: Int,
    val roomDescription: String,
)