package pl.poznan.put.hotel.room.query.handling

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import pl.poznan.put.hotel.room.query.model.RoomCheckoutScheduleEntity

@Repository
interface RoomCheckoutScheduleRepository : MongoRepository<RoomCheckoutScheduleEntity, Int>