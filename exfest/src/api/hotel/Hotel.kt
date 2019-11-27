package api.hotel

import model.hotel.RequestHotel
import repository.hotel.HotelsRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

const val HOTELS = "/hotels"
@KtorExperimentalLocationsAPI
@Location(HOTELS)

class Hotel

@KtorExperimentalLocationsAPI
fun Route.hotels(db: HotelsRepository) {
    authenticate("jwt") {
        get<Hotel> {
            val phrases = db.hotels()
            call.respond(phrases)
        }
        post<Hotel> {
            val request = call.receive<RequestHotel>()
            db.add(
                request.name,
                request.dateArrival,
                request.dateExit,
                request.dateBooking,
                request.cost,
                request.distance,
                request.transport,
                request.hotelPayed,
                request.userPayed)
            call.respond(HttpStatusCode.Created)
        }
        delete<Hotel> {
            db.clear()
            call.respond(HttpStatusCode.OK, "Hotel deleted")
        }
    }
}