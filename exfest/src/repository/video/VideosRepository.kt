package repository.video

import model.video.Video

interface VideosRepository {
    suspend fun add(title: String, size: Float, visits: Int, topic: Int, url: String)
    suspend fun video(id: Int): Video?
    suspend fun video(id: String): Video?
    suspend fun videos(): List<Video>
    suspend fun update(title: String, size: Float, visits: Int, topic: Int, url: String)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}