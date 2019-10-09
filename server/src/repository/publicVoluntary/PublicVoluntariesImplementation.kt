package repository.publicVoluntary

import model.publicVoluntary.PublicVoluntaries
import model.publicVoluntary.PublicVoluntary
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PublicVoluntariesImplementation : PublicVoluntariesRepository {
    private fun toPublicVoluntary(row: ResultRow): PublicVoluntary =
        PublicVoluntary(
            id = row[PublicVoluntaries.id],
            name = row[PublicVoluntaries.name],
            image = row[PublicVoluntaries.image],
            surname = row[PublicVoluntaries.surname],
            company = row[PublicVoluntaries.company],
            gdg = row[PublicVoluntaries.gdg],
            email = row[PublicVoluntaries.email]
        )

    override suspend fun add(
        id: Int,
        name: String,
        surname: String,
        image: String,
        company: String,
        gdg: String,
        email: String
    ) {
        transaction {
            PublicVoluntaries.insert {
                it [this.id] = id
                it [this.name] = name
                it [this.surname] = surname
                it [this.image] =  image
                it [this.company] = company
                it [this.gdg] = gdg
                it [this.email] = email
            }
        }
    }

    override suspend fun publicVoluntary(id: Int): PublicVoluntary? = dbQuery {
        PublicVoluntaries.select {
            (PublicVoluntaries.id eq id)
        }.mapNotNull { toPublicVoluntary(it) }.singleOrNull()
    }

    override suspend fun publicVoluntary(id: String): PublicVoluntary? {
        return publicVoluntary(id.toInt())
    }

    override suspend fun publicVoluntaries(): List<PublicVoluntary> = dbQuery {
        PublicVoluntaries.selectAll().map { toPublicVoluntary(it) }
    }

    override suspend fun update(
        id: Int,
        name: String,
        surname: String,
        image: String,
        company: String,
        gdg: String,
        email: String
    ) {
        transaction {
            PublicVoluntaries.update {
                it [this.id] = id
                it [this.name] = name
                it [this.surname] = surname
                it [this.image] =  image
                it [this.company] = company
                it [this.gdg] = gdg
                it [this.email] = email
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (publicVoluntary(id) == null) {
            throw  IllegalArgumentException("No phrase found for id ${publicVoluntary(id)}")
        }
        return dbQuery {
            PublicVoluntaries.deleteWhere { PublicVoluntaries.id eq id } > 0
        }    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        publicVoluntaries().map { remove(it.id) }
    }

}