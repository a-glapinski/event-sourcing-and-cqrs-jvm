package pl.poznan.put.hotel.booking.payment.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.util.*

@Document
data class PaymentEntity(
    @Id
    val paymentId: UUID,
    val accountId: UUID,
    val totalAmount: BigDecimal,
    var paymentStatus: PaymentStatusEntity,
)
