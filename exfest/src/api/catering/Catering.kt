package api.catering

import model.catering.RequestCatering
import repository.catering.CateringsRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

const val CATERINGS = "/caterings"
@KtorExperimentalLocationsAPI
@Location(CATERINGS)

class Catering

@KtorExperimentalLocationsAPI
fun Route.caterings(db: CateringsRepository) {
    authenticate("jwt") {
        get<Catering> {
            val catering = db.caterings()
            call.respond(catering)
        }
        post<Catering> {
            val request = call.receive<RequestCatering>()
            db.add(
                request.name,
                request.cost,
                request.mandatory,
                request.foodRestriction,
                request.dateService,
                request.dateRequest)
            call.respond(HttpStatusCode.Created)
        }
        delete<Catering> {
            db.clear()
            call.respond(HttpStatusCode.OK, "Catering deleted")
        }
    }
}