package pl.poznan.put.hotel.room.query.dto

import pl.poznan.put.hotel.room.query.model.FailedBookingEntity
import java.time.Instant

data class FailedBookingDto(
    val startDate: Instant?,
    val endDate: Instant?,
    val reason: String,
) {
    constructor(failedBookingEntity: FailedBookingEntity) : this(
        startDate = failedBookingEntity.startDate,
        endDate = failedBookingEntity.endDate,
        reason = failedBookingEntity.reason
    )
}