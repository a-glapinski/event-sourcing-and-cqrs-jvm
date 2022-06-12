package pl.poznan.put.hotel.booking.account.command.dto

data class AccountRequest(
    val username: String,
    val password: String,
)