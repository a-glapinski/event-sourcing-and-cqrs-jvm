package pl.poznan.put.hotel.booking.account.event

import java.util.*

data class AccountRegisteredEvent(
    val accountId: UUID,
    val username: String,
    val password: String,
)