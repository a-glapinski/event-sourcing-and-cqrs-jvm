package pl.poznan.put.hotel.room.query.coreapi

import java.util.*

data class FindRoomAvailabilityForAccountQuery(val roomId: Int, val accountId: UUID)
