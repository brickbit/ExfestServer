package repository.rating

import model.rating.Rating
import model.rating.Ratings
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


class RatingsImplementation : RatingsRepository {
    private fun toRating(row: ResultRow): Rating =
        Rating(
            id = row[Ratings.id],
            rate =  row[Ratings.rate],
            opinion = row[Ratings.opinion]
        )
    override suspend fun add(rate: Int, opinion: String) {
        transaction {
            Ratings.insert {
                it [this.rate] = rate
                it [this.opinion] = opinion
            }
        }
    }

    override suspend fun rating(id: Int): Rating? = dbQuery {
        Ratings.select {
            (Ratings.id eq id)
        }.mapNotNull { toRating(it) }.singleOrNull()
    }

    override suspend fun rating(id: String): Rating? {
        return rating(id.toInt())
    }

    override suspend fun ratings(): List<Rating> = dbQuery {
        Ratings.selectAll().map { toRating(it) }
    }

    override suspend fun update(id: Int, opinion: String) {
        transaction {
            Ratings.update {
                it [this.rate] = id
                it [this.opinion] = opinion
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (rating(id) == null) {
            throw  IllegalArgumentException("No rating found for id ${rating(id)}")
        }
        return dbQuery {
            Ratings.deleteWhere { Ratings.id eq id } > 0
        }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        ratings().map { remove(it.id) }
    }

}