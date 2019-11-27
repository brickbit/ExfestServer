package repository.merchandising

import model.merchandising.Merchandising
import model.merchandising.Merchandisings
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class MerchandisingsImplementation : MerchandisingsRepository {
    private fun toMerchanding(row: ResultRow): Merchandising =
        Merchandising(
            id = row[Merchandisings.id],
            name = row[Merchandisings.name],
            size = row[Merchandisings.size],
            cost = row[Merchandisings.cost],
            genre = row[Merchandisings.genre],
            customized = row[Merchandisings.customized]
        )
    override suspend fun add(
        name: String,
        size: String,
        cost: Float,
        genre: String,
        customized: Boolean
    ) {
        transaction {
            Merchandisings.insert {
                it [this.name] = name
                it [this.size] = size
                it [this.cost] = cost
                it [this.genre] = genre
                it [this.customized] = customized
            }
        }
    }

    override suspend fun merchandising(id: Int): Merchandising? = dbQuery {
        Merchandisings.select {
            (Merchandisings.id eq id)
        }.mapNotNull { toMerchanding(it) }.singleOrNull()
    }

    override suspend fun merchandising(id: String): Merchandising? {
        return merchandising(id.toInt())
    }

    override suspend fun merchandisings(): List<Merchandising> = dbQuery {
        Merchandisings.selectAll().map { toMerchanding(it) }
    }

    override suspend fun update(
        name: String,
        size: String,
        cost: Float,
        genre: String,
        customized: Boolean
    ) {
        transaction {
            Merchandisings.update {
                it [this.name] = name
                it [this.size] = size
                it [this.cost] = cost
                it [this.genre] = genre
                it [this.customized] = customized
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (merchandising(id) == null) {
            throw  IllegalArgumentException("No phrase found for id ${merchandising(id)}")
        }
        return dbQuery {
            Merchandisings.deleteWhere { Merchandisings.id eq id } > 0
        }    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        merchandisings().map { remove(it.id) }    }


}