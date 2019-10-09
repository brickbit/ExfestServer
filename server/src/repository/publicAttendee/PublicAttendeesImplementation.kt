package repository.publicAttendee

import model.publicAttendee.PublicAttendee
import model.publicAttendee.PublicAttendees
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PublicAttendeesImplementation : PublicAttendeesRepository {
    private fun toPubicAttendee(row: ResultRow): PublicAttendee =
        PublicAttendee(
            id = row[PublicAttendees.id],
            name = row[PublicAttendees.name],
            surname = row[PublicAttendees.surname],
            company = row[PublicAttendees.company],
            email = row[PublicAttendees.email]
        )
    override suspend fun add(
        id: Int,
        name: String,
        surname: String,
        company: String,
        email: String
    ) {
        transaction {
            PublicAttendees.insert {
                it [this.id] = id
                it [this.name] = name
                it [this.surname] = surname
                it [this.company] = company
                it [this.email] = email
            }
        }
    }

    override suspend fun publicAttendee(id: Int): PublicAttendee? = dbQuery {
        PublicAttendees.select {
            (PublicAttendees.id eq id)
        }.mapNotNull { toPubicAttendee(it) }.singleOrNull()
    }

    override suspend fun publicAttendee(id: String): PublicAttendee? {
        return publicAttendee(id.toInt())
    }

    override suspend fun publicAttendees(): List<PublicAttendee> = dbQuery {
        PublicAttendees.selectAll().map { toPubicAttendee(it) }
    }

    override suspend fun update(
        id: Int,
        name: String,
        surname: String,
        company: String,
        email: String
    ) {
        transaction {
            PublicAttendees.update {
                it [this.id] = id
                it [this.name] = name
                it [this.surname] = surname
                it [this.company] = company
                it [this.email] = email
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (publicAttendee(id) == null) {
            throw  IllegalArgumentException("No phrase found for id ${publicAttendee(id)}")
        }
        return dbQuery {
            PublicAttendees.deleteWhere { PublicAttendees.id eq id } > 0
        }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        publicAttendees().map { remove(it.id) }
    }

}