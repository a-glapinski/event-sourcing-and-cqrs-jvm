package pl.poznan.put.hotel.booking.account.query.dto

import pl.poznan.put.hotel.booking.account.query.model.AccountEntity
import java.util.*

data class AccountResponse(
    val accountId: UUID,
    val userName: String,
    val password: String,
) {
    constructor(accountEntity: AccountEntity) : this(
        accountId = accountEntity.accountId,
        userName = accountEntity.userName,
        password = accountEntity.password
    )
}