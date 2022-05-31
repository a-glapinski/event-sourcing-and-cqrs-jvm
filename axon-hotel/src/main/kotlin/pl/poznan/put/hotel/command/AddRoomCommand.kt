package pl.poznan.put.hotel.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class AddRoomCommand(
    @TargetAggregateIdentifier
    val roomNumber: Int,
    val roomDescription: String,
)