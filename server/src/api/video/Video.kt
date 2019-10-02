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

const val VIDEOS = "/videos"
@KtorExperimentalLocationsAPI
@Location(VIDEOS)

class Video

@KtorExperimentalLocationsAPI
fun Route.videos(db: VideosRepository) {
    get<Video> {
        val videos = db.videos()
        call.respond(videos)
    }
    authenticate("jwt") {
        post<Video> {
            val request = call.receive<RequestVideo>()
            db.add(
                request.title,
                request.size,
                request.visits,
                request.topic,
                request.url)
            call.respond(HttpStatusCode.Created)
        }
        delete<Video> {
            db.clear()
            call.respond(HttpStatusCode.OK, "Video deleted")
        }
    }
}