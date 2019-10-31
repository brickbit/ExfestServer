package repository.organizer

import model.organizer.Organizer

interface OrganizersRepository {
    suspend fun add(name: String,
                    surname: String,
                    foodRestriction: String,
                    email: String,
                    password: String,
                    moreInfo: String,
                    image: String,
                    company: String,
                    gdg: String
    )

    suspend fun organizer(id: Int): Organizer?
    suspend fun organizerByEmail(email: String): Organizer?
    suspend fun organizer(id: String): Organizer?
    suspend fun organizer(email: String, hash: String? = null): Organizer?
    suspend fun organizers(): List<Organizer>
    suspend fun update(name: String,
                       surname: String,
                       foodRestriction: String,
                       email: String,
                       password: String,
                       moreInfo: String,
                       image: String,
                       company: String,
                       gdg: String
    )
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}