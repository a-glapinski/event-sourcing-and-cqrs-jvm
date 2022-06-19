package pl.poznan.put.hotel.booking.account.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class AccountEntity(
    @Id
    val accountId: UUID,
    val username: String,
    val password: String,
)