package pl.poznan.put.hotel.inventory.room.query

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import pl.poznan.put.hotel.inventory.room.query.model.RoomEntity
import java.util.*

@Repository
interface RoomEntityRepository : MongoRepository<RoomEntity, UUID>