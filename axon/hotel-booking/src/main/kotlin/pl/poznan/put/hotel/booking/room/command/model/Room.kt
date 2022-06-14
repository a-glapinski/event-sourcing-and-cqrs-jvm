package pl.poznan.put.hotel.booking.room.command.model

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.extensions.kotlin.applyEvent
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateMember
import org.axonframework.spring.stereotype.Aggregate
import pl.poznan.put.hotel.booking.room.command.coreapi.AddRoomCommand
import pl.poznan.put.hotel.booking.room.command.coreapi.BookRoomCommand
import pl.poznan.put.hotel.booking.room.command.coreapi.CheckInCommand
import pl.poznan.put.hotel.booking.room.command.coreapi.CheckOutCommand
import pl.poznan.put.hotel.booking.room.command.coreapi.MarkRoomAsPreparedCommand
import pl.poznan.put.hotel.booking.room.event.coreapi.RoomAddedEvent
import pl.poznan.put.hotel.booking.room.event.coreapi.RoomBookedEvent
import pl.poznan.put.hotel.booking.room.event.coreapi.RoomBookingRejectedEvent
import pl.poznan.put.hotel.booking.room.event.coreapi.RoomCheckedInEvent
import pl.poznan.put.hotel.booking.room.event.coreapi.RoomCheckedOutEvent
import pl.poznan.put.hotel.booking.room.event.coreapi.RoomPreparedEvent

@Aggregate(snapshotTriggerDefinition = "roomSnapshotTriggerDefinition", cache = "cache")
class Room {
    @AggregateIdentifier
    private var number: Int? = null

    private lateinit var roomStatus: RoomStatus

    @AggregateMember
    private val bookings = mutableListOf<Booking>()

    private constructor()

    @CommandHandler
    constructor(command: AddRoomCommand) {
        applyEvent(RoomAddedEvent(command.roomNumber, command.roomDescription))
    }

    // TODO Check Account invariant
    @CommandHandler
    fun handle(command: BookRoomCommand) {
        when (command.roomBooking.isBookingAllowed()) {
            true -> applyEvent(RoomBookedEvent(command.roomNumber, command.roomBooking))
            false -> applyEvent(
                RoomBookingRejectedEvent(
                    roomNumber = command.roomNumber,
                    roomBooking = command.roomBooking,
                    reason = "Room $number is not available"
                )
            )
        }
    }

    @CommandHandler
    fun handle(command: MarkRoomAsPreparedCommand) {
        bookings.asSequence()
            .filter { it.roomBookingId == command.roomBookingId }
            .firstOrNull()
            ?.let { booking ->
                applyEvent(
                    RoomPreparedEvent(
                        roomNumber = command.roomNumber,
                        roomBooking = RoomBooking(
                            startDate = booking.startDate,
                            endDate = booking.endDate,
                            accountId = booking.accountId,
                            bookingId = booking.roomBookingId
                        )
                    )
                )
            }
    }

    @CommandHandler
    fun handle(command: CheckInCommand) {
        require(roomIsPrepared()) { "Room $number is not prepared" }
        applyEvent(RoomCheckedInEvent(command.roomNumber, command.roomBookingId))
    }

    @CommandHandler
    fun handle(command: CheckOutCommand) {
        require(roomIsCheckedIn()) { "Room $number is not checked-in" }
        applyEvent(RoomCheckedOutEvent(command.roomNumber, command.roomBookingId))
    }

    @EventSourcingHandler
    fun on(event: RoomAddedEvent) {
        number = event.roomNumber
        roomStatus = RoomStatus.EMPTY
    }

    @EventSourcingHandler
    fun on(event: RoomBookedEvent) {
        bookings.add(
            Booking(
                roomBookingId = event.roomBooking.bookingId,
                startDate = event.roomBooking.startDate,
                endDate = event.roomBooking.endDate,
                accountId = event.roomBooking.accountId
            )
        )
    }

    @EventSourcingHandler
    fun on(event: RoomPreparedEvent) {
        roomStatus = RoomStatus.PREPARED
    }

    @EventSourcingHandler
    fun on(event: RoomCheckedInEvent) {
        roomStatus = RoomStatus.CHECKED_IN
    }

    @EventSourcingHandler
    fun on(event: RoomCheckedOutEvent) {
        roomStatus = RoomStatus.EMPTY
    }

    private fun roomIsPrepared(): Boolean =
        roomStatus == RoomStatus.PREPARED

    private fun roomIsCheckedIn(): Boolean =
        roomStatus == RoomStatus.CHECKED_IN

    private fun RoomBooking.isBookingAllowed(): Boolean =
        bookings.none {
            it.startDate.isBefore(this.endDate) && it.endDate.isAfter(this.startDate)
        }
}