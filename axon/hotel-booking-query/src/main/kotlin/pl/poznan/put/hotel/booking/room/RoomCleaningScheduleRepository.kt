package pl.poznan.put.hotel.booking.room

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import pl.poznan.put.hotel.booking.room.model.RoomCleaningScheduleEntity

@Repository
interface RoomCleaningScheduleRepository : MongoRepository<RoomCleaningScheduleEntity, Int>