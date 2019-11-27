package model.video

import model.topic.Topics
import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Video(val id: Int,
                val title: String,
                val size: Float,
                val visits: Int,
                val topic: Int,
                val url: String): Serializable

object Videos: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val title = varchar("title",512)
    val size = float("size")
    val visits = integer("visits")
    val topic = reference("topic", Topics.id)
    val url = text("url")
}