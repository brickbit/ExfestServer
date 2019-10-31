package repository.organizer

import model.organizer.Organizer
import model.organizer.Organizers
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class OrganizersImplementation : OrganizersRepository {
    private fun toOrganizer(row: ResultRow): Organizer =
        Organizer(
            id = row[Organizers.id],
            name = row[Organizers.name],
            surname = row[Organizers.surname],
            foodRestriction = row[Organizers.foodRestriction],
            email = row[Organizers.email],
            password = row[Organizers.password],
            moreInfo = row[Organizers.moreInfo],
            image = row[Organizers.image],
            company = row[Organizers.company],
            gdg = row[Organizers.gdg]
        )
    override suspend fun add(name: String,
                             surname: String,
                             foodRestriction: String,
                             email: String,
                             password: String,
                             moreInfo: String,
                             image: String,
                             company: String,
                             gdg: String
    ) {
        transaction {
            Organizers.insert {
                it [this.name] = name
                it [this.surname] = surname
                it [this.foodRestriction] = foodRestriction
                it [this.email] = email
                it [this.password] = password
                it [this.moreInfo] = moreInfo
                it [this.image] = image
                it [this.company] = company
                it [this.gdg] = password
            }
        }
    }

    override suspend fun organizer(id: Int): Organizer? = dbQuery {
        Organizers.select {
            (Organizers.id eq id)
        }.mapNotNull { toOrganizer(it) }.singleOrNull()
    }

    override suspend fun organizerByEmail(email: String): Organizer? = dbQuery {
        Organizers.select {
            (Organizers.email eq email)
        }.mapNotNull { toOrganizer(it) }.singleOrNull()
    }

    override suspend fun organizer(id: String): Organizer? {
        return organizer(id.toInt())
    }

    override suspend fun organizer(email: String, hash: String?): Organizer? {

        val organizer = dbQuery {
            Organizers.select{
                (Organizers.email eq email)
            }.mapNotNull { toOrganizer(it) }.singleOrNull()
        }
        print(organizer)
        return when {
            organizer == null-> {
                null
            }
            hash == null -> {
                organizer
            }
            organizer.password == hash -> {
                organizer
            }
            else -> {
                null
            }
        }
    }

    override suspend fun organizers(): List<Organizer> = dbQuery {
        Organizers.selectAll().map { toOrganizer(it) }
    }

    override suspend fun update(name: String,
                                surname: String,
                                foodRestriction: String,
                                email: String,
                                password: String,
                                moreInfo: String,
                                image: String,
                                company: String,
                                gdg: String
    ) {
        transaction {
            Organizers.insert {
                it [this.name] = name
                it [this.surname] = surname
                it [this.foodRestriction] = foodRestriction
                it [this.email] = email
                it [this.password] = password
                it [this.moreInfo] = moreInfo
                it [this.image] = image
                it [this.company] = company
                it [this.gdg] = password
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (organizer(id) == null) {
            throw  IllegalArgumentException("No phrase found for id ${organizer(id)}")
        }
        return dbQuery {
            Organizers.deleteWhere { Organizers.id eq id } > 0
        }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        organizers().map { remove(it.id) }
    }

}