import io.ktor.application.call
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respondText
import io.ktor.routing.Route

const val HOME = "/"
@KtorExperimentalLocationsAPI
@Location(HOME)

class Home

@KtorExperimentalLocationsAPI
fun Route.home() {
    get<Home> {
        call.respondText("Hello ktor!!")
    }
}