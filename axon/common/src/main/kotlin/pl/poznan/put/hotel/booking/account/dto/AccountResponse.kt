package pl.poznan.put.hotel.booking.account.dto

import java.util.*

data class AccountResponse(
    val accountId: UUID,
    val username: String,
    val password: String,
)