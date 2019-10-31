package api.organizer

import model.organizer.RequestOrganizer
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import repository.organizer.OrganizersRepository
import repository.publicOrganizer.PublicOrganizersRepository

const val ORGANIZERS = "/organizers"
@KtorExperimentalLocationsAPI
@Location(ORGANIZERS)

class Organizer

@KtorExperimentalLocationsAPI
fun Route.organizers(db: OrganizersRepository, dbPublic: PublicOrganizersRepository) {
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
                request.foodRestriction,
                request.email,
                request.password,
                request.moreInfo,
                request.image,
                request.company,
                request.gdg)
            when (db.organizers().find { it.email == request.email }) {
                null -> call.respond(
                    HttpStatusCode.NotFound,
                    Error("Public partner could not be created")
                )
                else -> {
                    val organizer = db.organizers().find { it.email == request.email }
                    dbPublic.add(
                        organizer!!.id,
                        request.name,
                        request.surname,
                        request.moreInfo,
                        request.image,
                        request.company,
                        request.gdg,
                        request.email
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
        delete<Organizer> {
            dbPublic.clear()
            db.clear()
            call.respond(HttpStatusCode.OK, "Organizer deleted")
        }
    }
}