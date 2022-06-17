package pl.poznan.put.hotel.booking.account.web

import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway
import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway
import org.springframework.stereotype.Service
import pl.poznan.put.hotel.booking.account.command.RegisterAccountCommand
import pl.poznan.put.hotel.booking.account.dto.AccountRequest
import pl.poznan.put.hotel.booking.account.dto.AccountResponse
import pl.poznan.put.hotel.booking.account.query.FindAccountQuery
import pl.poznan.put.util.axon.queryUpdates
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.util.function.component1
import reactor.kotlin.core.util.function.component2
import java.time.Duration
import java.util.*

@Service
class AccountCommandService(
    private val reactorCommandGateway: ReactorCommandGateway,
    private val reactorQueryGateway: ReactorQueryGateway,
) {
    fun register(accountRequest: AccountRequest): Mono<AccountResponse> {
        val accountId = UUID.randomUUID()
        return reactorCommandGateway.send<AccountResponse>(
            RegisterAccountCommand(
                accountId,
                accountRequest.username,
                accountRequest.password
            )
        ).transform {
            Mono.zip(
                it.subscribeOn(Schedulers.parallel()),
                accountSubscriptionQuery(accountId).subscribeOn(Schedulers.parallel())
            ).map { (_, t2) -> t2 }
        }
    }

    private fun accountSubscriptionQuery(accountId: UUID): Mono<AccountResponse> =
        reactorQueryGateway.queryUpdates<_, AccountResponse>(FindAccountQuery(accountId))
            .next()
            .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))

    companion object {
        private const val TIMEOUT_SECONDS = 5L
    }
}