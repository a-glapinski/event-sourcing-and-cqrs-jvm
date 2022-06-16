package pl.poznan.put.hotel.booking.account

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import pl.poznan.put.hotel.booking.account.model.AccountEntity
import java.util.*

@Repository
interface AccountEntityRepository : MongoRepository<AccountEntity, UUID>