package pl.poznan.put.hotel.booking.account.dto

import java.util.*

data class AccountRequest(
    val accountId: UUID,
    val username: String,
    val password: String,
)