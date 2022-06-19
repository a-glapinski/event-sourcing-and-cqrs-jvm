package pl.poznan.put.hotel.booking.account.dto

import pl.poznan.put.hotel.booking.account.model.AccountEntity
import java.util.*

data class AccountResponse(
    val accountId: UUID,
    val username: String,
    val password: String,
) {
    constructor(accountEntity: AccountEntity) : this(
        accountId = accountEntity.accountId,
        username = accountEntity.username,
        password = accountEntity.password
    )
}