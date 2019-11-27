package repository.foodRestriction

import model.foodRestriction.FoodRestriction
import model.foodRestriction.FoodRestrictions
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import repository.DatabaseFactory.dbQuery


class FoodRestrictionImplementation : FoodRestrictionRepository {
    private fun toFoodRestriction(row: ResultRow): FoodRestriction =
        FoodRestriction(
            id = row[FoodRestrictions.id],
            name = row[FoodRestrictions.name],
            idAttendee = row[FoodRestrictions.idAttendee],
            idOrganizer = row[FoodRestrictions.idOrganizer],
            idSpeaker = row[FoodRestrictions.idSpeaker],
            idVoluntary = row[FoodRestrictions.idVoluntary]
        )

    override suspend fun add(
        name: String,
        idAttendee: Int,
        idOrganizer: Int,
        idSpeaker: Int,
        idVoluntary: Int
    ) {
        transaction {
            FoodRestrictions.insert {
                it [this.name] = name
                it [this.idAttendee] = idAttendee
                it [this.idOrganizer] = idOrganizer
                it [this.idSpeaker] = idSpeaker
                it [this.idVoluntary] = idVoluntary
            }
        }
    }

    override suspend fun foodRestriction(id: Int): FoodRestriction? = dbQuery {
        FoodRestrictions.select {
            (FoodRestrictions.id eq id)
        }.mapNotNull { toFoodRestriction(it) }.singleOrNull()
    }

    override suspend fun foodRestriction(id: String): FoodRestriction? {
        return foodRestriction(id.toInt())
    }

    override suspend fun foodRestrictions(): List<FoodRestriction> = dbQuery {
        FoodRestrictions.selectAll().map { toFoodRestriction(it) }
    }

    override suspend fun update(
        name: String,
        idAttendee: Int,
        idOrganizer: Int,
        idSpeaker: Int,
        idVoluntary: Int
    ) {
        transaction {
            FoodRestrictions.update {
                it [this.name] = name
                it [this.idAttendee] = idAttendee
                it [this.idOrganizer] = idOrganizer
                it [this.idSpeaker] = idSpeaker
                it [this.idVoluntary] = idVoluntary
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (foodRestriction(id) == null) {
            throw  IllegalArgumentException("No food restriction found for id ${foodRestriction(id)}")
        }
        return dbQuery {
            FoodRestrictions.deleteWhere { FoodRestrictions.id eq id } > 0
        }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        foodRestrictions().map { remove(it.id) }    }

}