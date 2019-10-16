package api.auth

import JWTService
import hash
import model.organizer.RequestOrganizer
import io.ktor.application.call
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.util.KtorExperimentalAPI
import model.Token
import repository.organizer.OrganizersRepository

const val LOGIN_ENDPOINT = "/login"
@KtorExperimentalLocationsAPI
@Location(LOGIN_ENDPOINT)

class Login

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Route.login(db: OrganizersRepository, jwtService: JWTService) {
    post<Login> {
        val request = call.receive<RequestOrganizer>()

        val email = request.email
        val password = request.password
        val user = db.organizer(email, hash(password))

        if (user!=null) {
            val token = jwtService.generateToken(user)
            val key = Token(key = token)
            call.respond(key)
        } else {
            call.respondText("Invalid user")
        }
    }
}