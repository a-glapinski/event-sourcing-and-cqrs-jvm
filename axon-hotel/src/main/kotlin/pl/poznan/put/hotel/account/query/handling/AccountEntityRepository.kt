package pl.poznan.put.hotel.account.query.handling

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import pl.poznan.put.hotel.account.query.model.AccountEntity
import java.util.*

@Repository
interface AccountEntityRepository : MongoRepository<AccountEntity, UUID>