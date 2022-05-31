package pl.poznan.put.hotel.room.model

import org.axonframework.modelling.command.EntityId
import java.time.Instant
import java.util.*

data class Booking(
    @EntityId
    val roomBookingId: UUID,
    val startDate: Instant,
    val endDate: Instant,
    val accountId: UUID,
)
