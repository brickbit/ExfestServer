package repository.publicPartner

import model.publicPartner.PublicPartner

interface PublicPartnersRepository {
    suspend fun add(
        id: Int,
        name: String,
        image: String,
        category: String,
        email: String)
    suspend fun publicPartner(id: Int): PublicPartner?
    suspend fun publicPartner(id: String): PublicPartner?
    suspend fun publicPartners(): List<PublicPartner>
    suspend fun update(
        id: Int,
        name: String,
        image: String,
        category: String,
        email: String)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}