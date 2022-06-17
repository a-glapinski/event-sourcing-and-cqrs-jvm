package pl.poznan.put.hotel.booking.payment.web

import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway
import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway
import org.springframework.stereotype.Service
import pl.poznan.put.hotel.booking.payment.command.PayCommand
import pl.poznan.put.hotel.booking.payment.command.ProcessPaymentCommand
import pl.poznan.put.hotel.booking.payment.dto.PayRequest
import pl.poznan.put.hotel.booking.payment.dto.PaymentResponse
import pl.poznan.put.hotel.booking.payment.query.FindPaymentQuery
import pl.poznan.put.util.axon.queryUpdates
import pl.poznan.put.util.axon.sendAny
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.*

@Service
class PaymentCommandService(
    private val reactorCommandGateway: ReactorCommandGateway,
    private val reactorQueryGateway: ReactorQueryGateway,
) {
    fun pay(payRequest: PayRequest): Mono<UUID> {
        val paymentId = UUID.randomUUID()
        return Mono
            .`when`(subscribeToPaymentUpdates(paymentId))
            .and(
                reactorCommandGateway.sendAny(
                    PayCommand(
                        paymentId = paymentId,
                        accountId = payRequest.accountId,
                        totalAmount = payRequest.totalAmount
                    )
                )
            )
            .then(Mono.just(paymentId))
    }

    fun process(paymentId: UUID): Mono<UUID> =
        Mono
            .`when`(subscribeToPaymentUpdates(paymentId))
            .and(reactorCommandGateway.sendAny(ProcessPaymentCommand(paymentId)))
            .then(Mono.just(paymentId))

    private fun subscribeToPaymentUpdates(paymentId: UUID): Mono<PaymentResponse> =
        reactorQueryGateway
            .queryUpdates<_, PaymentResponse>(FindPaymentQuery(paymentId))
            .next()
            .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))

    companion object {
        private const val TIMEOUT_SECONDS = 5L
    }
}