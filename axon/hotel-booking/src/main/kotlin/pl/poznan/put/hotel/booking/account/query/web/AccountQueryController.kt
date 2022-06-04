package pl.poznan.put.hotel.booking.account.query.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.hotel.booking.account.query.dto.AccountResponse
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/accounts")
class AccountQueryController(
    private val accountQueryService: AccountQueryService,
) {
    @GetMapping("/{accountId}")
    fun account(@PathVariable accountId: UUID): Mono<AccountResponse> =
        accountQueryService.get(accountId)

    @GetMapping
    fun getAll(): Mono<List<AccountResponse>> =
        accountQueryService.getAll()
}