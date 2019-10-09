package repository.publicPartner

import model.partner.Partners
import model.publicPartner.PublicPartner
import model.publicPartner.PublicPartners
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PublicPartnersImplementation : PublicPartnersRepository {
    private fun toPublicPartner(row: ResultRow): PublicPartner =
        PublicPartner(
            id = row[PublicPartners.id],
            name = row[PublicPartners.name],
            image = row[PublicPartners.image],
            category = row[PublicPartners.category],
            email = row[PublicPartners.email]
        )

    override suspend fun add(
        id: Int,
        name: String,
        image: String,
        category: String,
        email: String
    ) {
        transaction {
            PublicPartners.insert {
                it [this.id] = id
                it [this.name] = name
                it [this.image] = image
                it [this.category] = category
                it [this.email] = email
            }
        }
    }

    override suspend fun publicPartner(id: Int): PublicPartner? = dbQuery {
        PublicPartners.select {
            (PublicPartners.id eq id)
        }.mapNotNull { toPublicPartner(it) }.singleOrNull()
    }

    override suspend fun publicPartner(id: String): PublicPartner? {
        return publicPartner(id.toInt())
    }

    override suspend fun publicPartners(): List<PublicPartner> = dbQuery {
        PublicPartners.selectAll().map { toPublicPartner(it) }
    }

    override suspend fun update(
        id: Int,
        name: String,
        image: String,
        category: String,
        email: String
    ) {
        transaction {
            PublicPartners.update {
                it [this.id] = id
                it [this.name] = name
                it [this.image] = image
                it [this.category] = category
                it [this.email] = email
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (publicPartner(id) == null) {
            throw  IllegalArgumentException("No phrase found for id ${publicPartner(id)}")
        }
        return dbQuery {
            PublicPartners.deleteWhere { PublicPartners.id eq id } > 0
        }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        print(publicPartners())
        publicPartners().map { remove(it.id) }
    }
}