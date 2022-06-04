package pl.poznan.put.hotel.payment.command.web

import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway
import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway
import org.springframework.stereotype.Service
import pl.poznan.put.hotel.payment.command.coreapi.PayCommand
import pl.poznan.put.hotel.payment.command.coreapi.ProcessPaymentCommand
import pl.poznan.put.hotel.payment.command.dto.PayRequest
import pl.poznan.put.hotel.payment.query.coreapi.FindPaymentQuery
import pl.poznan.put.hotel.payment.query.dto.PaymentResponse
import pl.poznan.put.hotel.util.axon.queryUpdates
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
                reactorCommandGateway.send<PaymentResponse>(
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
            .and(reactorCommandGateway.send<PaymentResponse>(ProcessPaymentCommand(paymentId)))
            .then(Mono.just(paymentId))

    private fun subscribeToPaymentUpdates(paymentId: UUID): Mono<PaymentResponse> =
        reactorQueryGateway
            .queryUpdates<PaymentResponse, FindPaymentQuery>(FindPaymentQuery(paymentId))
            .next()
            .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))

    companion object {
        private const val TIMEOUT_SECONDS = 5L
    }
}