package pl.poznan.put.hotel.account.query.coreapi

import java.util.*

data class FindAccountQuery(
    val accountId: UUID,
)