package pl.poznan.put.hotel.command.event

import java.util.*

data class AccountRegisteredEvent(
    val accountId: UUID,
    val userName: String,
    val password: String,
)