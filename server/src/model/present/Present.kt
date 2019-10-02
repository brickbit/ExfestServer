package model.present

import model.attendee.Attendees
import model.merchandising.Merchandisings
import model.partner.Partners
import model.speaker.Speakers
import model.voluntary.Voluntaries
import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Present(
    val id: Int,
    val name: String,
    val cost: Float,
    val description: String,
    val speaker: Int?,
    val voluntary: Int?,
    val attendee: Int?,
    val granted: Boolean,
    val partner: Int?
): Serializable

object Presents: Table() {
    val id = Merchandisings.integer("id").primaryKey().autoIncrement()
    val name  = varchar("day",128)
    val cost = float("cost")
    val description = text("description")
    val speaker = reference("speaker", Speakers.id).nullable()
    val voluntary = reference("voluntary", Voluntaries.id).nullable()
    val attendee = reference("attendee", Attendees.id).nullable()
    val granted = bool("granted")
    val partner = reference("partner", Partners.id).nullable()
}