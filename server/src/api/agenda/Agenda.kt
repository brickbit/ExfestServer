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

const val AGENDAS = "/agendas"
@KtorExperimentalLocationsAPI
@Location(AGENDAS)

class Agendas

@KtorExperimentalLocationsAPI
fun Route.agendas(db: AgendasRepository) {
    authenticate("jwt") {
        get<Agendas> {
            val phrases = db.agendas()
            call.respond(phrases)
        }
        post<Agendas> {
            val request = call.receive<RequestAgenda>()
                db.add(
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
        delete<Agendas> {
            db.clear()
            call.respond(HttpStatusCode.OK, "Agenda deleted")
        }
    }
}