package api.place

import model.place.RequestPlace
import repository.place.PlacesRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

const val PLACES = "/places"
@KtorExperimentalLocationsAPI
@Location(PLACES)

class Place

@KtorExperimentalLocationsAPI
fun Route.places(db: PlacesRepository) {
    authenticate("jwt") {
        get<Place> {
            val phrases = db.places()
            call.respond(phrases)
        }
        post<Place> {
            val request = call.receive<RequestPlace>()
            db.add(
                request.day,
                request.cost,
                request.xCoordinate,
                request.yCoordinate,
                request.description,
                request.indication,
                request.image,
                request.mandatoryCatering)
            call.respond(HttpStatusCode.Created)
        }
        delete<Place> {
            db.clear()
            call.respond(HttpStatusCode.OK, "Place deleted")
        }
    }
}