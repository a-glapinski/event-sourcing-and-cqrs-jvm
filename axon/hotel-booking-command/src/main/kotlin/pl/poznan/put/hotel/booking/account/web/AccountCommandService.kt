package pl.poznan.put.hotel.booking.account.web

import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway
import org.springframework.stereotype.Service
import pl.poznan.put.hotel.booking.account.command.RegisterAccountCommand
import pl.poznan.put.hotel.booking.account.dto.AccountRequest
import pl.poznan.put.util.axon.sendAny
import reactor.core.publisher.Mono

@Service
class AccountCommandService(
    private val reactorCommandGateway: ReactorCommandGateway,
) {
    fun register(accountRequest: AccountRequest): Mono<Void> =
        reactorCommandGateway.sendAny(
            RegisterAccountCommand(
                accountId = accountRequest.accountId,
                username = accountRequest.username,
                password = accountRequest.password
            )
        )
}