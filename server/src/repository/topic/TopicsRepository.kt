package repository.topic

import model.topic.Topic

interface TopicsRepository {
    suspend fun add(name: String, image: String)
    suspend fun topic(id: Int): Topic?
    suspend fun topic(id: String): Topic?
    suspend fun topics(): List<Topic>
    suspend fun update(name: String, image: String)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}