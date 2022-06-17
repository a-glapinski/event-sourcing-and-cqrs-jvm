package pl.poznan.put.hotel.booking.account.dto

import pl.poznan.put.hotel.booking.account.model.AccountEntity

fun AccountEntity.toResponse() = AccountResponse(
    accountId = accountId,
    username = username,
    password = password
)