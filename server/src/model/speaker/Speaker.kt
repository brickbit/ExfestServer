package model.speaker

import model.hotel.Hotels
import model.merchandising.Merchandisings
import model.transport.Transports
import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Speaker(
    val id: Int,
    val name: String,
    val foodRestriction: String,
    val surname: String,
    val genre: String,
    val transport: Int,
    val merchandising: Int?,
    val moreInfo: String,
    val children: Boolean,
    val comeChildren: Boolean,
    val nChildren: Int,
    val ageChildren: Int,
    val hotel: Int?,
    val cost: Float,
    val numberVisit: Int,
    val image: String,
    val company: String,
    val year: String,
    val email: String): Serializable

object Speakers: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 255)
    val surname = varchar("surname", 255)
    val foodRestriction = varchar("food_restriction", 255)
    val genre = varchar("genre", 255)
    val transport = reference("transport", Transports.id)
    val merchandising = reference("merchandising", Merchandisings.id).nullable()
    val moreInfo = text("more_info")
    val children = bool("children")
    val comeChildren = bool("come_children")
    val nChildren = integer("n_children")
    val ageChildren = integer("age_children")
    val hotel = reference("hotel", Hotels.id).nullable()
    val cost = float("cost")
    val numberVisit = integer("number_visit")
    val image = text("image")
    val company = varchar("company",255)
    val year = varchar("year", 255)
    val email = varchar("email", 64)
}