package repository.place

import model.place.Place
import model.place.Places
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PlacesImplementation : PlacesRepository {
    private fun toPlace(row: ResultRow): Place =
        Place(
            id = row[Places.id],
            day =  row[Places.day],
            cost = row[Places.cost],
            xCoordinate = row[Places.xCoordinate],
            yCoordinate = row[Places.yCoordinate],
            description = row[Places.description],
            indication = row[Places.indication],
            image = row[Places.image],
            mandatoryCatering = row[Places.mandatoryCatering]
        )
    override suspend fun add(
        day: String,
        cost: Float,
        xCoordinate: Float,
        yCoordinate: Float,
        description: String,
        indication: String,
        image: String,
        mandatoryCatering: Boolean
    ) {
        transaction {
            Places.insert {
                it [this.day] = day
                it [this.cost] = cost
                it [this.xCoordinate] = xCoordinate
                it [this.yCoordinate] = yCoordinate
                it [this.description] = description
                it [this.indication] = indication
                it [this.image] =  image
                it [this.mandatoryCatering] = mandatoryCatering
            }
        }
    }

    override suspend fun place(id: Int): Place? = dbQuery {
        Places.select {
            (Places.id eq id)
        }.mapNotNull { toPlace(it) }.singleOrNull()
    }

    override suspend fun place(id: String): Place? {
        return place(id.toInt())
    }

    override suspend fun places(): List<Place>  = dbQuery {
            Places.selectAll().map { toPlace(it) }
    }

    override suspend fun update(
        day: String,
        cost: Float,
        xCoordinate: Float,
        yCoordinate: Float,
        description: String,
        indication: String,
        image: String,
        mandatoryCatering: Boolean
    ) {
        transaction {
            Places.update {
                it [this.day] = day
                it [this.cost] = cost
                it [this.xCoordinate] = xCoordinate
                it [this.yCoordinate] = yCoordinate
                it [this.description] = description
                it [this.indication] = indication
                it [this.image] =  image
                it [this.mandatoryCatering] = mandatoryCatering
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (place(id) == null) {
            throw  IllegalArgumentException("No phrase found for id ${place(id)}")
        }
        return dbQuery {
            Places.deleteWhere { Places.id eq id } > 0
        }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        places().map { remove(it.id) }
    }

}