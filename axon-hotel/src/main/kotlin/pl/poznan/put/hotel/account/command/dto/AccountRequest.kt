package pl.poznan.put.hotel.account.command.dto

data class AccountRequest(
    val userName: String,
    val password: String,
)