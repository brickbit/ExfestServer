package api.video

import model.video.RequestVideo
import repository.video.VideosRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
@Location("$VIDEOS/{id}")

data class VideoDetail(val id: Int)

@KtorExperimentalLocationsAPI
fun Route.videosDetail(db: VideosRepository) {
    get<VideoDetail> { item ->
        when (db.videos().find { it.id == item.id } ) {
            null -> call.respond(HttpStatusCode.NotFound, Error("Agenda with id ${item.id} not found"))
            else -> {
                call.respond(db.video(item.id)!!)
            }
        }
    }
    authenticate("jwt") {
        delete<VideoDetail> { item ->
            when (db.videos().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Video with id ${item.id} not found"))
                else -> {
                    db.remove(item.id)
                    call.respond(HttpStatusCode.OK, "Video $item.id deleted")
                }
            }
        }
        post<VideoDetail> { item ->
            val request = call.receive<RequestVideo>()
            when (db.videos().find { it.id == item.id }) {
                null -> call.respond(HttpStatusCode.NotFound, Error("Video with id ${item.id} not found"))
                else -> {
                    db.update(
                        request.title,
                        request.size,
                        request.visits,
                        request.topic,
                        request.url
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}