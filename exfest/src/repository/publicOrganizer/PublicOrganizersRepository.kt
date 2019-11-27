package repository.publicOrganizer

import model.publicOrganizer.PublicOrganizer

interface PublicOrganizersRepository {
    suspend fun add(
        id: Int,
        name: String,
        surname: String,
        moreInfo: String,
        image: String,
        company: String,
        gdg: String,
        email: String
    )

    suspend fun publicOrganizer(id: Int): PublicOrganizer?
    suspend fun publicOrganizer(id: String): PublicOrganizer?
    suspend fun publicOrganizers(): List<PublicOrganizer>
    suspend fun update(
        id: Int,
        name: String,
        surname: String,
        moreInfo: String,
        image: String,
        company: String,
        gdg: String,
        email: String
    )
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}