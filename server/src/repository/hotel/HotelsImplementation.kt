package repository.hotel

import model.hotel.Hotel
import model.hotel.Hotels
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class HotelsImplementation : HotelsRepository {
    private fun toHotel(row: ResultRow): Hotel =
        Hotel(
            id = row[Hotels.id],
            dateArrival = row[Hotels.dateArrival],
            dateExit = row[Hotels.dateExit],
            dateBooking = row[Hotels.dateBooking],
            cost = row[Hotels.cost],
            distance = row[Hotels.distance],
            transport = row[Hotels.transport],
            hotelPayed =  row[Hotels.hotelPayed],
            userPayed =  row[Hotels.userPayed],
            rating = row[Hotels.rating]
        )

    override suspend fun add(
        dayArrival: String,
        dayExit: String,
        dateBooking: String,
        cost: Float,
        distance: Float,
        transport: String,
        hotelPayed: Boolean,
        userPayed: Boolean,
        rating: Int
    ) {
        transaction {
            Hotels.insert {
                it [this.dateArrival] = dayArrival
                it [this.dateExit] = dayExit
                it [this.dateBooking] = dateBooking
                it [this.cost] = cost
                it [this.distance] = distance
                it [this.transport] = transport
                it [this.hotelPayed] =  hotelPayed
                it [this.userPayed] = userPayed
                it [this.rating] = rating
            }
        }
    }

    override suspend fun hotel(id: Int): Hotel? = dbQuery {
        Hotels.select {
            (Hotels.id eq id)
        }.mapNotNull { toHotel(it) }.singleOrNull()
    }

    override suspend fun hotel(id: String): Hotel? {
        return hotel(id.toInt())
    }

    override suspend fun hotels(): List<Hotel> = dbQuery {
        Hotels.selectAll().map { toHotel(it) }
    }

    override suspend fun update(
        dayArrival: String,
        dayExit: String,
        dateBooking: String,
        cost: Float,
        distance: Float,
        transport: String,
        hotelPayed: Boolean,
        userPayed: Boolean,
        rating: Int
    ) {
        transaction {
            Hotels.update {
                it [this.dateArrival] = dayArrival
                it [this.dateExit] = dayExit
                it [this.dateBooking] = dateBooking
                it [this.cost] = cost
                it [this.distance] = distance
                it [this.transport] = transport
                it [this.hotelPayed] =  hotelPayed
                it [this.userPayed] = userPayed
                it [this.rating] = rating
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (hotel(id) == null) {
            throw  IllegalArgumentException("No phrase found for id ${hotel(id)}")
        }
        return dbQuery {
            Hotels.deleteWhere { Hotels.id eq id } > 0
        }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        hotels().map { remove(it.id) }    }

}