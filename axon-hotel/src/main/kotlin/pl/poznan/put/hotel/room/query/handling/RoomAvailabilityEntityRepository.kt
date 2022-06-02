package pl.poznan.put.hotel.room.query.handling

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import pl.poznan.put.hotel.room.query.model.RoomAvailabilityEntity

@Repository
interface RoomAvailabilityEntityRepository : MongoRepository<RoomAvailabilityEntity, Int>