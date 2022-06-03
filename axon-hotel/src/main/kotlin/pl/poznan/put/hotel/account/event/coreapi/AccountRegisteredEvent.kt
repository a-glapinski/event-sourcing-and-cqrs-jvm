package pl.poznan.put.hotel.account.event.coreapi

import java.util.*

data class AccountRegisteredEvent(
    val accountId: UUID,
    val userName: String,
    val password: String,
)