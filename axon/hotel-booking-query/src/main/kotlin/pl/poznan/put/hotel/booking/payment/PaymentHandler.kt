package pl.poznan.put.hotel.booking.payment

import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.extensions.kotlin.emit
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.stereotype.Component
import pl.poznan.put.hotel.booking.payment.dto.PaymentResponse
import pl.poznan.put.hotel.booking.payment.dto.toResponse
import pl.poznan.put.hotel.booking.payment.event.PaymentRequestedEvent
import pl.poznan.put.hotel.booking.payment.event.PaymentSucceededEvent
import pl.poznan.put.hotel.booking.payment.model.PaymentEntity
import pl.poznan.put.hotel.booking.payment.model.PaymentStatusEntity
import pl.poznan.put.hotel.booking.payment.query.FindPaymentQuery
import pl.poznan.put.hotel.booking.payment.query.FindPaymentsForAccountQuery
import pl.poznan.put.hotel.booking.payment.query.FindPaymentsQuery
import pl.poznan.put.util.repository.findByIdOrThrow

@Component
@ProcessingGroup("payment")
class PaymentHandler(
    private val queryUpdateEmitter: QueryUpdateEmitter,
    private val paymentEntityRepository: PaymentEntityRepository,
) {
    @EventHandler
    fun on(event: PaymentRequestedEvent) {
        PaymentEntity(
            paymentId = event.paymentId,
            accountId = event.accountId,
            totalAmount = event.totalAmount,
            paymentStatus = PaymentStatusEntity.PROCESSING
        )
            .let { paymentEntityRepository.save(it) }
            .also {
                queryUpdateEmitter.emit(it.toResponse()) { query: FindPaymentQuery ->
                    query.paymentId == event.paymentId
                }
            }
            .also {
                queryUpdateEmitter.emit(it.toResponse()) { query: FindPaymentsForAccountQuery ->
                    query.accountId == event.accountId
                }
            }
            .also {
                queryUpdateEmitter.emit(it.toResponse()) { _: FindPaymentsQuery -> true }
            }
    }

    @EventHandler
    fun on(event: PaymentSucceededEvent) {
        paymentEntityRepository.findByIdOrThrow(event.paymentId)
            .apply {
                paymentStatus = PaymentStatusEntity.SUCCEEDED
            }
            .let { paymentEntityRepository.save(it) }
            .also {
                queryUpdateEmitter.emit(it.toResponse()) { query: FindPaymentQuery ->
                    query.paymentId == event.paymentId
                }
            }
            .also {
                queryUpdateEmitter.emit(it.toResponse()) { query: FindPaymentsForAccountQuery ->
                    query.accountId == it.accountId
                }
            }
            .also {
                queryUpdateEmitter.emit(it.toResponse()) { _: FindPaymentsQuery -> true }
            }
    }

    @QueryHandler
    fun handle(query: FindPaymentQuery): PaymentResponse =
        paymentEntityRepository
            .findByIdOrThrow(query.paymentId)
            .toResponse()

    @QueryHandler
    fun handle(query: FindPaymentsQuery): List<PaymentResponse> =
        paymentEntityRepository.findAll()
            .map { it.toResponse() }

    @QueryHandler
    fun handle(query: FindPaymentsForAccountQuery): List<PaymentResponse> =
        paymentEntityRepository.findByAccountId(query.accountId)
            .map { it.toResponse() }
}