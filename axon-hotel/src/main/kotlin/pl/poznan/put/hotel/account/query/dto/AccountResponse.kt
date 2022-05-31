package pl.poznan.put.hotel.account.query.dto

data class AccountResponse(
    val accountId: String,
    val userName: String,
    val password: String,
)