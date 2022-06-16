package pl.poznan.put.hotel.booking.payment

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import pl.poznan.put.hotel.booking.payment.model.PaymentEntity
import java.util.*

@Repository
interface PaymentEntityRepository : MongoRepository<PaymentEntity, UUID> {
    fun findByAccountId(accountId: UUID): List<PaymentEntity>
}