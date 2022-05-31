package pl.poznan.put.hotel.room.query

import java.util.*

data class FindRoomAvailabilityForAccount(val roomId: Int, val accountId: UUID)
