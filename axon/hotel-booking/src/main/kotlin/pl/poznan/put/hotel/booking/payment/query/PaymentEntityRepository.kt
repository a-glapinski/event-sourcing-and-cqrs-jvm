package pl.poznan.put.hotel.booking.payment.query

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import pl.poznan.put.hotel.booking.payment.query.model.PaymentEntity
import java.util.*

@Repository
interface PaymentEntityRepository : MongoRepository<PaymentEntity, UUID> {
    fun findByAccountId(accountId: UUID): List<PaymentEntity>
}