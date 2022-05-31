package pl.poznan.put.hotel.account.query.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class AccountEntity(
    @Id
    var accountId: String,
    var userName: String,
    var password: String,
)