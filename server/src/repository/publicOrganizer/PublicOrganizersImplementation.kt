package repository.publicOrganizer

import model.publicOrganizer.PublicOrganizer
import model.publicOrganizer.PublicOrganizers
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PublicOrganizersImplementation : PublicOrganizersRepository {
    private fun toPublicOrganizer(row: ResultRow): PublicOrganizer =
        PublicOrganizer(
            id = row[PublicOrganizers.id],
            name = row[PublicOrganizers.name],
            surname = row[PublicOrganizers.surname],
            moreInfo = row[PublicOrganizers.moreInfo],
            image = row[PublicOrganizers.image],
            company = row[PublicOrganizers.company],
            gdg = row[PublicOrganizers.gdg],
            email = row[PublicOrganizers.email]
        )
    override suspend fun add(
        id: Int,
        name: String,
        surname: String,
        moreInfo: String,
        image: String,
        company: String,
        gdg: String,
        email: String
    ) {
        transaction {
            PublicOrganizers.insert {
                it [this.id] = id
                it [this.name] = name
                it [this.surname] = surname
                it [this.moreInfo] = moreInfo
                it [this.image] = image
                it [this.company] = company
                it [this.gdg] = gdg
                it [this.email] = email
            }
        }
    }

    override suspend fun publicOrganizer(id: Int): PublicOrganizer? = dbQuery {
        PublicOrganizers.select {
            (PublicOrganizers.id eq id)
        }.mapNotNull { toPublicOrganizer(it) }.singleOrNull()
    }

    override suspend fun publicOrganizer(id: String): PublicOrganizer? {
        return publicOrganizer(id.toInt())
    }

    override suspend fun publicOrganizers(): List<PublicOrganizer> = dbQuery {
        PublicOrganizers.selectAll().map { toPublicOrganizer(it) }
    }

    override suspend fun update(
        id: Int,
        name: String,
        surname: String,
        moreInfo: String,
        image: String,
        company: String,
        gdg: String,
        email: String
    ) {
        transaction {
            PublicOrganizers.update {
                it [this.id] = id
                it [this.name] = name
                it [this.surname] = surname
                it [this.moreInfo] = moreInfo
                it [this.image] = image
                it [this.company] = company
                it [this.gdg] = gdg
                it [this.email] = email
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (publicOrganizer(id) == null) {
            throw  IllegalArgumentException("No phrase found for id ${publicOrganizer(id)}")
        }
        return dbQuery {
            PublicOrganizers.deleteWhere { PublicOrganizers.id eq id } > 0
        }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        publicOrganizers().map { remove(it.id) }
    }

}