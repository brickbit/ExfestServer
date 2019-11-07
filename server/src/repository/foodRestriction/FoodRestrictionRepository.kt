package repository.foodRestriction

import model.foodRestriction.FoodRestriction
import model.hotel.Hotel

interface FoodRestrictionRepository {
    suspend fun add( name: String,
                     idAttendee: Int,
                     idOrganizer: Int,
                     idSpeaker: Int,
                     idVoluntary: Int)
    suspend fun foodRestriction(id: Int): FoodRestriction?
    suspend fun foodRestriction(id: String): FoodRestriction?
    suspend fun foodRestrictions(): List<FoodRestriction>
    suspend fun update(name: String,
                       idAttendee: Int,
                       idOrganizer: Int,
                       idSpeaker: Int,
                       idVoluntary: Int)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}