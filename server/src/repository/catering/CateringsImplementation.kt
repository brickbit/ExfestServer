package repository.catering

import model.catering.Catering
import model.catering.Caterings
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class CateringsImplementation : CateringsRepository {
    private fun toCatering(row: ResultRow): Catering =
        Catering(
            id = row[Caterings.id],
            name = row[Caterings.name],
            cost = row[Caterings.cost],
            mandatory = row[Caterings.mandatory],
            rating = row[Caterings.rating],
            numberDiner = row[Caterings.numberDiner],
            dateService =  row[Caterings.dateService],
            dateRequest =  row[Caterings.dateRequest]
        )
    override suspend fun add(
        name: String,
        cost: Float,
        mandatory: Boolean,
        rating: Int,
        numberDiner: Int,
        dateService: String,
        dateRequest: String
    ) {
        transaction {
            Caterings.insert {
                it [this.name] = name
                it [this.cost] = cost
                it [this.mandatory] = mandatory
                it [this.rating] = rating
                it [this.numberDiner] =  numberDiner
                it [this.dateService] = dateService
                it [this.dateRequest] =  dateRequest
            }
        }
    }

    override suspend fun catering(id: Int): Catering? = dbQuery {
        Caterings.select {
            (Caterings.id eq id)
        }.mapNotNull { toCatering(it) }.singleOrNull()
    }

    override suspend fun catering(id: String): Catering? {
        return catering(id.toInt())
    }

    override suspend fun caterings(): List<Catering>  = dbQuery {
        Caterings.selectAll().map { toCatering(it) }
    }

    override suspend fun update(
        name: String,
        cost: Float,
        mandatory: Boolean,
        rating: Int,
        numberDiner: Int,
        dateService: String,
        dateRequest: String
    ) {
        transaction {
            Caterings.update {
                it [this.name] = name
                it [this.cost] = cost
                it [this.mandatory] = mandatory
                it [this.rating] = rating
                it [this.numberDiner] =  numberDiner
                it [this.dateService] = dateService
                it [this.dateRequest] =  dateRequest
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (catering(id) == null) {
            throw  IllegalArgumentException("No phrase found for id ${catering(id)}")
        }
        return dbQuery {
            Caterings.deleteWhere { Caterings.id eq id } > 0
        }    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        caterings().map { remove(it.id) }    }
}