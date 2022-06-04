package pl.poznan.put.hotel.room.command.dto

import pl.poznan.put.hotel.room.command.model.RoomBooking
import java.time.Instant
import java.util.*

data class RoomBookingDto(
    val startDate: Instant,
    val endDate: Instant,
    val accountId: UUID,
) {
    fun toDomain() = RoomBooking(
        startDate = startDate,
        endDate = endDate,
        accountId = accountId
    )
}