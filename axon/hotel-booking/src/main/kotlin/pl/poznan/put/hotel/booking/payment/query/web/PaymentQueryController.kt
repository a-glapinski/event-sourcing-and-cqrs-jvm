package pl.poznan.put.hotel.booking.payment.query.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.hotel.booking.payment.query.dto.PaymentResponse
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/payments")
class PaymentQueryController(
    private val paymentQueryService: PaymentQueryService,
) {
    @GetMapping("/{paymentId}")
    fun get(@PathVariable paymentId: UUID): Mono<PaymentResponse> =
        paymentQueryService.get(paymentId)

    @GetMapping("/account/{accountId}")
    fun getAll(@PathVariable accountId: UUID): Mono<List<PaymentResponse>> =
        paymentQueryService.getAll(accountId)

    @GetMapping
    fun getAll(): Mono<List<PaymentResponse>> =
        paymentQueryService.getAll()
}