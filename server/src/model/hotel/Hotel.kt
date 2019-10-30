package model.hotel

import model.rating.Ratings
import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Hotel(
    val id: Int,
    val name: String,
    val dateArrival: String,
    val dateExit: String,
    val dateBooking: String,
    val cost: Float,
    val distance: Float,
    val transport: String,
    val hotelPayed: Boolean,
    val userPayed: Boolean,
    val rating: Int?
): Serializable

object Hotels: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name",255)
    val dateArrival = varchar("day_arrival",255)
    val dateExit = varchar("day_exit",255)
    val dateBooking = varchar("day_booking",255)
    val cost = float("cost")
    val distance = float("distance")
    val transport = varchar("transport",255)
    val hotelPayed = bool("hotel_payed")
    val userPayed = bool("user_payed")
    val rating = reference("rating", Ratings.id).nullable()
}