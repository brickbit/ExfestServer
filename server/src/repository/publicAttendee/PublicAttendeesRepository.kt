package repository.publicAttendee

import model.publicAttendee.PublicAttendee

interface PublicAttendeesRepository {
    suspend fun add(id: Int,
                    name: String,
                    surname: String,
                    company: String,
                    email: String)
    suspend fun publicAttendee(id: Int): PublicAttendee?
    suspend fun publicAttendee(id: String): PublicAttendee?
    suspend fun publicAttendees(): List<PublicAttendee>
    suspend fun update(
                    id: Int,
                    name: String,
                    surname: String,
                    company: String,
                    email: String)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}