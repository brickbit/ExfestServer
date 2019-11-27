package repository.publicVoluntary

import model.publicVoluntary.PublicVoluntary

interface PublicVoluntariesRepository {
    suspend fun add(
        id: Int,
        name: String,
        surname: String,
        image: String,
        company: String,
        gdg: String,
        email: String)
    suspend fun publicVoluntary(id: Int): PublicVoluntary?
    suspend fun publicVoluntary(id: String): PublicVoluntary?
    suspend fun publicVoluntaries(): List<PublicVoluntary>
    suspend fun update(
        id: Int,
        name: String,
        surname: String,
        image: String,
        company: String,
        gdg: String,
        email: String)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}