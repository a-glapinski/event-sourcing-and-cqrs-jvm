package pl.poznan.put.hotel.booking.payment.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.util.*

@Document
data class PaymentEntity(
    @Id
    var paymentId: UUID,
    var accountId: UUID,
    var totalAmount: BigDecimal,
    var paymentStatus: PaymentStatusEntity,
)
