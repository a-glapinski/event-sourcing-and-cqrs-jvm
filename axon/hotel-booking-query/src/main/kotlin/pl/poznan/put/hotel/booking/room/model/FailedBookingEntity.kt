package pl.poznan.put.hotel.booking.room.model

import java.time.Instant
import java.util.*

data class FailedBookingEntity(
    var id: UUID,
    var startDate: Instant?,
    var endDate: Instant?,
    var accountId: UUID,
    var reason: String,
)
