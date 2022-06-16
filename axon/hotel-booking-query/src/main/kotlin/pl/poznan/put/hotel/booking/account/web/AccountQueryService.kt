package pl.poznan.put.hotel.booking.account.web

import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway
import org.springframework.stereotype.Service
import pl.poznan.put.hotel.booking.account.dto.AccountResponse
import pl.poznan.put.hotel.booking.account.query.FindAccountQuery
import pl.poznan.put.hotel.booking.account.query.FindAccountsQuery
import pl.poznan.put.util.axon.query
import pl.poznan.put.util.axon.queryMany
import reactor.core.publisher.Mono
import java.util.*

@Service
class AccountQueryService(
    private val reactorQueryGateway: ReactorQueryGateway,
) {
    fun get(accountId: UUID): Mono<AccountResponse> =
        reactorQueryGateway.query(FindAccountQuery(accountId))

    fun getAll(): Mono<List<AccountResponse>> =
        reactorQueryGateway.queryMany(FindAccountsQuery())
}