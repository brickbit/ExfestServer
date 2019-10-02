package api.conference

import model.conference.RequestConference
import repository.conferences.ConferencesRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
@Location("$CONFERENCES/{id}")

data class ConferenceDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.conferenceDetail(db: ConferencesRepository) {
    authenticate("jwt") {
        get<ConferenceDetail> { item ->
            when (db.conferences().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Conference with id ${item.id} not found"))
                else -> {
                    call.respond(db.conference(item.id)!!)
                }
            }
        }
        delete<ConferenceDetail> { item ->
            when (db.conferences().find { it.id == item.id }) {
            null -> call.respond(HttpStatusCode.NotFound, Error("Conference with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "Conference $item.id deleted")
                }
            }
        }
        post<ConferenceDetail> { item ->
            val request = call.receive<RequestConference>()
            when(db.conferences().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Conference with id ${item.id} not found"))
                else -> {
                    db.update(
                        request.title,
                        request.hour,
                        request.duration,
                        request.speaker,
                        request.topic,
                        request.description,
                        request.streaming,
                        request.video
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}
