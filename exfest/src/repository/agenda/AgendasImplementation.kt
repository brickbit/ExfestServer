package repository.agenda

import model.agenda.Agenda
import model.agenda.Agendas
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class AgendasImplementation : AgendasRepository {
    private fun toAgenda(row: ResultRow): Agenda =
        Agenda(
            id = row[Agendas.id],
            day =  row[Agendas.day],
            speaker = row[Agendas.speaker],
            conference = row[Agendas.conference],
            place = row[Agendas.place],
            catering = row[Agendas.catering],
            service = row[Agendas.service],
            year = row[Agendas.year]
        )
    override suspend fun add(
        day: String,
        speaker: Int,
        conference: Int,
        place: Int,
        catering: Int,
        service: Int,
        year: Int
    ) {
        transaction {
            Agendas.insert {
                it [this.day] = day
                it [this.speaker] = speaker
                it [this.conference] = conference
                it [this.place] = place
                it [this.catering] = catering
                it [this.service] = service
                it [this.year] =  year
            }
        }
    }

    override suspend fun agenda(id: Int): Agenda? = dbQuery {
        Agendas.select {
            (Agendas.id eq id)
        }.mapNotNull { toAgenda(it) }.singleOrNull()
    }

    override suspend fun agendas(): List<Agenda> = dbQuery {
        Agendas.selectAll().map { toAgenda(it) }
    }

    override suspend fun update(
        day: String,
        speaker: Int,
        conference: Int,
        place: Int,
        catering: Int,
        service: Int,
        year: Int
    ) {
        transaction {
            Agendas.update {
                it[this.day] = day
                it[this.speaker] = speaker
                it[this.conference] = conference
                it[this.place] = place
                it[this.catering] = catering
                it[this.service] = service
                it[this.year] = year
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (agenda(id) == null) {
            throw  IllegalArgumentException("No agenda found for id ${agenda(id)}")
        }
        return dbQuery {
            Agendas.deleteWhere { Agendas.id eq id } > 0
        }
    }

    override suspend fun clear() {
        agendas().map { remove(it.id) }
    }

}