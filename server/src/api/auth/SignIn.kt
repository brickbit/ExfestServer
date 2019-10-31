package api.auth

import hash
import model.organizer.RequestOrganizer
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import repository.organizer.OrganizersRepository

const val SIGNIN = "/signin"

@KtorExperimentalLocationsAPI
@Location(SIGNIN)

data class SignIn(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val moreInfo: String = "",
    val image: String = "",
    val company: String = "",
    val gdg: String = "")

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
fun Route.signIn(db: OrganizersRepository) {
    post<SignIn> {
        val request = call.receive<RequestOrganizer>()
        db.add(
            request.name,
            request.surname,
            request.foodRestriction,
            request.email,
            hash(request.password),
            request.moreInfo,
            request.image,
            request.company,
            request.gdg
        )

        call.respond(HttpStatusCode.Created)
    }

}