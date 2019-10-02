package model.voluntary

import model.foodRestriction.FoodRestrictions
import model.hotel.Hotels
import model.merchandising.Merchandisings
import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Voluntary(
    val id: Int,
    val name: String,
    val surname: String,
    val genre: String,
    val transport: String,
    val foodRestriction: Int?,
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
    val dateRequestTicket: String,
    val dateGrantTicket: String,
    val datePayedTicket: String,
    val timesExpiredTicket: Int,
    val timesAbsent: Int,
    val gdg: String,
    val cost: Float): Serializable

object Voluntaries: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 255)
    val surname = varchar("surname", 255)
    val genre = varchar("genre", 255)
    val transport = varchar("transport", 128)
    val foodRestriction = reference("foodRestriction", FoodRestrictions.id).nullable()
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
    val dateRequestTicket = varchar("date_request_ticket", 32)
    val dateGrantTicket = varchar("date_grant_ticket", 32)
    val datePayedTicket = varchar("date_payed_ticket", 32)
    val timesExpiredTicket = integer("times_expired_ticket")
    val timesAbsent = integer("times_absent")
    val gdg = varchar("gdg", 128)
    val cost = float("cost")
}