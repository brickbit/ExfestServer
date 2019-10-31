package model.attendee

import model.hotel.Hotels
import model.merchandising.Merchandisings
import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Attendee(
    val id: Int,
    val name: String,
    val surname: String,
    val foodRestriction: String,
    val genre: String,
    val transport: String,
    val merchandising: Int?,
    val moreInfo: String,
    val children: Boolean,
    val comeChildren: Boolean,
    val nChildren: Int,
    val ageChildren: Int,
    val hotel: Int?,
    val priceTicket: Float,
    val numberVisit: Int,
    val image: String,
    val company: String,
    val rating: Int,
    val dateRequestTicket: String,
    val dateGrantTicket: String,
    val datePayedTicket: String,
    val timesExpiredTicket: Int,
    val timesAbsent: Int,
    val email: String): Serializable

object Attendees: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 255)
    val surname = varchar("surname", 255)
    val foodRestriction = varchar("foodRestriction", 255)
    val genre = varchar("genre", 255)
    val transport = varchar("transport", 128)
    val merchandising = reference("merchandising", Merchandisings.id).nullable()
    val moreInfo = text("more_info")
    val children = bool("children")
    val comeChildren = bool("come_children")
    val nChildren = integer("n_children")
    val ageChildren = integer("age_children")
    val hotel = reference("hotel", Hotels.id).nullable()
    val priceTicket = float("price_ticket")
    val numberVisit = integer("number_visit")
    val image = text("image")
    val company = varchar("company",255)
    val rating = integer("rating")
    val dateRequestTicket = varchar("date_request_ticket", 32)
    val dateGrantTicket = varchar("date_grant_ticket", 32)
    val datePayedTicket = varchar("date_payed_ticket", 32)
    val timesExpiredTicket = integer("times_expired_ticket")
    val timesAbsent = integer("times_absent")
    val email = varchar("email", 64)
}