package pl.poznan.put.hotel.booking.room.valueobject

import java.time.Instant
import java.util.*

data class RoomBooking(
    val startDate: Instant,
    val endDate: Instant,
    val accountId: UUID,
    val bookingId: UUID,
) {
    constructor(
        startDate: Instant,
        endDate: Instant,
        accountId: UUID,
    ) : this(
        startDate = startDate,
        endDate = endDate,
        accountId = accountId,
        bookingId = UUID.randomUUID()
    )
}
