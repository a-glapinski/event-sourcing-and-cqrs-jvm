package pl.poznan.put.hotel.booking.account.command.web

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.hotel.booking.account.command.dto.AccountRequest
import pl.poznan.put.hotel.booking.account.query.dto.AccountResponse
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/accounts")
class AccountCommandController(
    private val accountCommandService: AccountCommandService,
) {
    @PostMapping
    fun register(@RequestBody accountRequest: AccountRequest): Mono<AccountResponse> =
        accountCommandService.register(accountRequest)
}