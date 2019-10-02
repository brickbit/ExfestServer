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

@KtorExperimentalLocationsAPI
@Location("$TOPICS/{id}")

data class TopicDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.topicsDetail(db: TopicsRepository) {
    authenticate("jwt") {
        get<TopicDetail> { item ->
            when (db.topics().find { it.id == item.id } ) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Topic with id ${item.id} not found"))
                else -> {
                    call.respond(db.topic(item.id)!!)
                }
            }
        }
        delete<TopicDetail> { item ->
            when (db.topics().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Topic with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "Topic $item.id deleted")
                }
            }
        }
        post<TopicDetail> { item ->
            val request = call.receive<RequestTopic>()
            when (db.topics().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Topic with id ${item.id} not found"))
                else -> {
                    db.update(
                        request.name,
                        request.image)
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}