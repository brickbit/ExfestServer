package repository.video

import model.video.Video
import model.video.Videos
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class VideosImplementation : VideosRepository {
    private fun toVideo(row: ResultRow): Video =
        Video(
            id = row[Videos.id],
            title = row[Videos.title],
            size =  row[Videos.size],
            visits = row[Videos.visits],
            topic = row[Videos.topic],
            url = row[Videos.url]
        )
    override suspend fun add(title: String, size: Float, visits: Int, topic: Int, url: String) {
        transaction {
            Videos.insert {
                it [this.title] = title
                it [this.size] = size
                it [this.visits] = visits
                it [this.topic] = topic
                it [this.url] = url
            }
        }
    }

    override suspend fun video(id: Int): Video? = dbQuery {
        Videos.select {
            (Videos.id eq id)
        }.mapNotNull { toVideo(it) }.singleOrNull()
    }

    override suspend fun video(id: String): Video? {
        return video(id.toInt())
    }

    override suspend fun videos(): List<Video> = dbQuery {
        Videos.selectAll().map { toVideo(it) }
    }

    override suspend fun update(title: String, size: Float, visits: Int, topic: Int, url: String) {
        transaction {
            Videos.update {
                it [this.title] = title
                it [this.size] = size
                it [this.visits] = visits
                it [this.topic] = topic
                it [this.url] = url
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (video(id) == null) {
            throw  IllegalArgumentException("No rating found for id ${video(id)}")
        }
        return dbQuery {
            Videos.deleteWhere { Videos.id eq id } > 0
        }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        videos().map { remove(it.id) }
    }

}