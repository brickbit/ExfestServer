package model.foodRestriction

import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class FoodRestriction(
    val id: Int,
    val name: String,
    val idAttendee: Int,
    val idOrganizer: Int,
    val idSpeaker: Int,
    val idVoluntary: Int
): Serializable

object FoodRestrictions: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name",255)
    val idAttendee = integer("id_attendee")
    val idOrganizer = integer("id_organizer")
    val idSpeaker = integer("id_speaker")
    val idVoluntary = integer("id_voluntary")
}