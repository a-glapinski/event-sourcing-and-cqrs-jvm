package pl.poznan.put.hotel.booking.room.query.model

import java.time.Instant
import java.util.*

data class BookingEntity(
    var id: UUID,
    var startDate: Instant,
    var endDate: Instant,
    var accountId: UUID,
)