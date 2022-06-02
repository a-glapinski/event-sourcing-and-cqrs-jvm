package pl.poznan.put.hotel.account.query.handling

import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.extensions.kotlin.emit
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.stereotype.Component
import pl.poznan.put.hotel.account.event.AccountRegisteredEvent
import pl.poznan.put.hotel.account.query.FindAccount
import pl.poznan.put.hotel.account.query.FindAccounts
import pl.poznan.put.hotel.account.query.dto.AccountResponse
import pl.poznan.put.hotel.account.query.model.AccountEntity
import pl.poznan.put.hotel.util.findByIdOrThrow

@Component
@ProcessingGroup("account")
class AccountHandler(
    private val queryUpdateEmitter: QueryUpdateEmitter,
    private val accountEntityRepository: AccountEntityRepository,
) {
    @EventHandler
    fun on(event: AccountRegisteredEvent) {
        AccountEntity(
            accountId = event.accountId,
            userName = event.userName,
            password = event.password
        )
            .let { accountEntityRepository.save(it) }
            .also {
                queryUpdateEmitter.emit<FindAccount, AccountResponse>(
                    AccountResponse(it)
                ) { query ->
                    query.accountId == event.accountId
                }
            }
            .also {
                queryUpdateEmitter.emit<FindAccounts, AccountResponse>(AccountResponse(it)) { true }
            }
    }

    @QueryHandler
    fun handle(query: FindAccount): AccountResponse =
        AccountResponse(accountEntityRepository.findByIdOrThrow(query.accountId))

    @QueryHandler
    fun handle(query: FindAccounts): List<AccountResponse> =
        accountEntityRepository.findAll()
            .map { AccountResponse(it) }
}