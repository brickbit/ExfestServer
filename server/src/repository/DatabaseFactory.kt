package repository

import model.agenda.Agendas
import model.attendee.Attendees
import model.bill.Bills
import model.catering.Caterings
import model.conference.Conferences
import model.hotel.Hotels
import model.place.Places
import model.merchandising.Merchandisings
import model.organizer.Organizers
import model.partner.Partners
import model.present.Presents
import model.service.Services
import model.speaker.Speakers
import model.topic.Topics
import model.video.Videos
import model.voluntary.Voluntaries
import model.transport.Transports
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.foodRestriction.FoodRestrictions
import model.publicAttendee.PublicAttendees
import model.publicOrganizer.PublicOrganizers
import model.publicPartner.PublicPartners
import model.publicSpeaker.PublicSpeakers
import model.publicVoluntary.PublicVoluntaries
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import javax.xml.validation.Schema

object DatabaseFactory {
    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = System.getenv("JDBC_DATABASE_URL")
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }
    fun init() {
        Database.connect(hikari())
        transaction {
            SchemaUtils.create(Conferences)
            SchemaUtils.create(Speakers)
            SchemaUtils.create(Places)
            SchemaUtils.create(Hotels)
            SchemaUtils.create(Transports)
            SchemaUtils.create(Attendees)
            SchemaUtils.create(Voluntaries)
            SchemaUtils.create(Agendas)
            SchemaUtils.create(Videos)
            SchemaUtils.create(Partners)
            SchemaUtils.create(Merchandisings)
            SchemaUtils.create(Presents)
            SchemaUtils.create(Services)
            SchemaUtils.create(Caterings)
            SchemaUtils.create(Organizers)
            SchemaUtils.create(Bills)
            SchemaUtils.create(Topics)
            SchemaUtils.create(FoodRestrictions)

            SchemaUtils.create(PublicAttendees)
            SchemaUtils.create(PublicOrganizers)
            SchemaUtils.create(PublicPartners)
            SchemaUtils.create(PublicSpeakers)
            SchemaUtils.create(PublicVoluntaries)
        }
    }
    suspend fun <T> dbQuery(
        block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}