package api.topic

import model.topic.RequestTopic
import repository.topic.TopicsRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

const val TOPICS = "/topics"
@KtorExperimentalLocationsAPI
@Location(TOPICS)

class Topic

@KtorExperimentalLocationsAPI
fun Route.topics(db: TopicsRepository) {
    authenticate("jwt") {
        get<Topic> {
            val phrases = db.topics()
            call.respond(phrases)
        }
        post<Topic> {
            val request = call.receive<RequestTopic>()
            db.add(
                request.name,
                request.image)
            call.respond(HttpStatusCode.Created)
        }
        delete<Topic> {
            db.clear()
            call.respond(HttpStatusCode.OK, "Topic deleted")
        }
    }
}