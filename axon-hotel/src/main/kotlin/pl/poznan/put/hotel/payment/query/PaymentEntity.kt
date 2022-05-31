package pl.poznan.put.hotel.payment.query

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.util.*

@Document
data class PaymentEntity(
    @Id
    val paymentId: String,
    val accountId: UUID,
    val totalAmount: BigDecimal,
    val paymentStatus: PaymentStatusEntity,
)
