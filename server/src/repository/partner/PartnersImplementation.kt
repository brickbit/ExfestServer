package repository.partner

import model.partner.Partner
import model.partner.Partners
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PartnersImplementation : PartnersRepository {
    private fun toPartner(row: ResultRow): Partner =
        Partner(
            id = row[Partners.id],
            name = row[Partners.name],
            income = row[Partners.income],
            service = row[Partners.service],
            image = row[Partners.image],
            category = row[Partners.category]
        )

    override suspend fun add(
        name: String,
        income: Float,
        service: Int,
        image: String,
        category: String
    ) {
        transaction {
            Partners.insert {
                it [this.name] = name
                it [this.income] = income
                it [this.service] = service
                it [this.image] = image
                it [this.category] = category
            }
        }
    }

    override suspend fun partner(id: Int): Partner? = dbQuery {
        Partners.select {
            (Partners.id eq id)
        }.mapNotNull { toPartner(it) }.singleOrNull()
    }

    override suspend fun partner(id: String): Partner? {
        return partner(id.toInt())
    }

    override suspend fun partners(): List<Partner> = dbQuery {
        Partners.selectAll().map { toPartner(it) }
    }

    override suspend fun update(

        name: String,
        income: Float,
        service: Int,
        image: String,
        category: String
    ) {
        transaction {
            Partners.update {
                it [this.name] = name
                it [this.income] = income
                it [this.service] = service
                it [this.image] = image
                it [this.category] = category
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (partner(id) == null) {
            throw  IllegalArgumentException("No phrase found for id ${partner(id)}")
        }
        return dbQuery {
            Partners.deleteWhere { Partners.id eq id } > 0
        }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        partners().map { remove(it.id) }
    }
}