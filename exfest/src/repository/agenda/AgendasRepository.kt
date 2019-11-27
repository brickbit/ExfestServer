package repository.agenda

import model.agenda.Agenda

interface AgendasRepository {
    suspend fun add(day: String,
                    speaker: Int,
                    conference: Int,
                    place: Int,
                    catering: Int,
                    service: Int,
                    year: Int)
    suspend fun agenda(id: Int): Agenda?
    suspend fun agendas(): List<Agenda>
    suspend fun update(
                        day: String,
                        speaker: Int,
                        conference: Int,
                        place: Int,
                        catering: Int,
                        service: Int,
                        year: Int)
    suspend fun remove(id: Int): Boolean
    suspend fun clear()
}