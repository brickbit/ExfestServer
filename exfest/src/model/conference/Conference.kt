package model.conference


import model.speaker.Speakers
import model.topic.Topics
import model.video.Videos
import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Conference(
    val id: Int,
    val title: String,
    val hour: String,
    val duration: Float,
    val speaker: Int,
    val topic: Int,
    val description: String,
    val streaming: Boolean,
    val video: Int?
): Serializable

object Conferences: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val title = text("title")
    val hour = varchar("hour",8)
    val duration = float("duration")
    val speaker = reference("speaker", Speakers.id)
    val topic = reference("topic", Topics.id)
    val description = text("description")
    val streaming = bool("streaming")
    val video  = reference("video", Videos.id).nullable()
}