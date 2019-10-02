package api.organizer

import model.organizer.RequestOrganizer
import repository.organizer.OrganizersRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

const val ORGANIZERS = "/organizers"
@KtorExperimentalLocationsAPI
@Location(ORGANIZERS)

class Organizer

@KtorExperimentalLocationsAPI
fun Route.organizers(db: OrganizersRepository) {
    authenticate("jwt") {
        get<Organizer> {
            val organizers = db.organizers()
            call.respond(organizers)
        }
        post<Organizer> {
            val request = call.receive<RequestOrganizer>()
            db.add(
                request.name,
                request.surname,
                request.email,
                request.password)
            call.respond(HttpStatusCode.Created)
        }
        delete<Organizer> {
            db.clear()
            call.respond(HttpStatusCode.OK, "Organizer deleted")
        }
    }
}