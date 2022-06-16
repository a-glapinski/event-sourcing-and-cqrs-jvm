package pl.poznan.put.hotel.booking.room

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import pl.poznan.put.hotel.booking.room.model.RoomCheckoutScheduleEntity

@Repository
interface RoomCheckoutScheduleRepository : MongoRepository<RoomCheckoutScheduleEntity, Int>