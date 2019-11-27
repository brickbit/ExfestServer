package repository.publicSpeaker

import model.publicSpeaker.PublicSpeaker

interface PublicSpeakersRepository {
    suspend fun add(
        id: Int,
        name: String,
        surname: String,
        moreInfo: String,
        image: String,
        company: String,
        email: String)
    suspend fun publicSpeaker(id: Int): PublicSpeaker?
    suspend fun publicSpeaker(id: String): PublicSpeaker?
    suspend fun publicSpeakers(): List<PublicSpeaker>
    suspend fun update(
        id: Int,
        name: String,
        surname: String,
        moreInfo: String,
        image: String,
        company: String,
        email: String)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}