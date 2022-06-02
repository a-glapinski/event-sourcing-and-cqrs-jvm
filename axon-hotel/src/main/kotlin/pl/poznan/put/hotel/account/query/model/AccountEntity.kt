package pl.poznan.put.hotel.account.query.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class AccountEntity(
    @Id
    var accountId: UUID,
    var userName: String,
    var password: String,
)