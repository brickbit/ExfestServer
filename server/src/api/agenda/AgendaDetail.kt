package api.agenda

import model.agenda.RequestAgenda
import repository.agenda.AgendasRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
@Location("$AGENDAS/{id}")

data class AgendaDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.agendasDetail(db: AgendasRepository) {
    authenticate("jwt") {
        get<AgendaDetail> { item ->
            when (db.agendas().find { it.id == item.id } ) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Agenda with id ${item.id} not found"))
                else -> {
                    call.respond(db.agenda(item.id)!!)
                }
            }
        }
        delete<AgendaDetail> { item ->
            when (db.agendas().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Agenda with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "Agenda $item.id deleted")
                }
            }
        }
        post<AgendaDetail> { item->
            val request = call.receive<RequestAgenda>()
            when (db.agendas().find { it.id == item.id } ) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Agenda with id ${item.id} not found"))
                else -> {
                    db.update(
                        request.day,
                        request.speaker,
                        request.conference,
                        request.place,
                        request.catering,
                        request.service,
                        request.year
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}