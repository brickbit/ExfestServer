package api.merchandising

import model.merchandising.RequestMerchandising
import repository.merchandising.MerchandisingsRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

const val MERCHANDISINGS = "/merchandisings"
@KtorExperimentalLocationsAPI
@Location(MERCHANDISINGS)

class Merchandising

@KtorExperimentalLocationsAPI
fun Route.merchandisings(db: MerchandisingsRepository) {
    authenticate("jwt") {
        get<Merchandising> {
            val phrases = db.merchandisings()
            call.respond(phrases)
        }
        post<Merchandising> {
            val request = call.receive<RequestMerchandising>()
            db.add(
                request.name,
                request.size,
                request.cost,
                request.genre,
                request.customized)
            call.respond(HttpStatusCode.Created)
        }
        delete<Merchandising> {
            db.clear()
            call.respond(HttpStatusCode.OK, "Merchandising deleted")
        }
    }
}