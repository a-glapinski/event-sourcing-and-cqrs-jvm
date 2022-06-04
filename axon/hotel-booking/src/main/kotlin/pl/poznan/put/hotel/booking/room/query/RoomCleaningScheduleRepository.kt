package pl.poznan.put.hotel.booking.room.query

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import pl.poznan.put.hotel.booking.room.query.model.RoomCleaningScheduleEntity

@Repository
interface RoomCleaningScheduleRepository : MongoRepository<RoomCleaningScheduleEntity, Int>