package model.agenda

import model.catering.Caterings
import model.conference.Conferences
import model.place.Places
import model.service.Services
import model.speaker.Speakers
import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Agenda(
    val id: Int,
    val day: String,
    val speaker: Int,
    val conference: Int,
    val place: Int,
    val catering: Int,
    val service: Int?,
    val year: Int): Serializable

object Agendas: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val day = varchar("day",64)
    val speaker = integer("speaker").references(Speakers.id)
    val conference = integer("conference").references(Conferences.id)
    val place = integer("place").references(Places.id)
    val catering = integer("catering").references(Caterings.id)
    val service = integer("service").references(Services.id).nullable()
    val year = integer("year")
}