package pl.poznan.put.hotel.inventory.room.command.model

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.extensions.kotlin.applyEvent
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import pl.poznan.put.hotel.inventory.room.command.coreapi.AddRoomToInventoryCommand
import pl.poznan.put.hotel.inventory.room.command.coreapi.CreateRoomCommand
import pl.poznan.put.hotel.inventory.room.command.coreapi.MarkRoomAsAddedToBookingSystemCommand
import pl.poznan.put.hotel.inventory.room.event.coreapi.RoomAddedToBookingSystemEvent
import pl.poznan.put.hotel.inventory.room.event.coreapi.RoomAddedToInventoryEvent
import pl.poznan.put.hotel.inventory.room.event.coreapi.RoomCreatedEvent
import java.util.*
import kotlin.properties.Delegates

@Aggregate
class Room {
    @AggregateIdentifier
    lateinit var roomId: UUID

    var roomNumber by Delegates.notNull<Int>()

    lateinit var roomStatus: RoomStatus

    lateinit var description: String

    private constructor()

    @CommandHandler
    constructor(command: CreateRoomCommand) {
        applyEvent(
            RoomCreatedEvent(
                roomId = command.roomId,
                roomNumber = command.roomNumber,
                roomDescription = command.roomDescription
            )
        )
    }

    @CommandHandler
    fun handle(command: AddRoomToInventoryCommand) {
        require(RoomStatus.CREATED == roomStatus) { "This room is not created" }
        applyEvent(
            RoomAddedToInventoryEvent(
                roomId = command.roomId,
                roomNumber = roomNumber,
                roomDescription = description
            )
        )
    }

    @CommandHandler
    fun handle(command: MarkRoomAsAddedToBookingSystemCommand) {
        require(RoomStatus.IN_INVENTORY == roomStatus) { "This room is not in inventory" }
        applyEvent(
            RoomAddedToBookingSystemEvent(
                roomId = command.roomId
            )
        )
    }

    @EventSourcingHandler
    fun on(event: RoomCreatedEvent) {
        roomId = event.roomId
        roomNumber = event.roomNumber
        description = event.roomDescription
        roomStatus = RoomStatus.CREATED
    }

    @EventSourcingHandler
    fun on(event: RoomAddedToInventoryEvent) {
        roomStatus = RoomStatus.IN_INVENTORY
    }

    @EventSourcingHandler
    fun on(event: RoomAddedToBookingSystemEvent) {
        roomStatus = RoomStatus.IN_BOOKING_SYSTEM
    }
}