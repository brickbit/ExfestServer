package repository.conferences

import model.conference.Conference

interface ConferencesRepository {
    suspend fun add(title: String,
                    hour: String,
                    duration: Float,
                    speaker: Int,
                    topic: Int,
                    description: String,
                    streaming: Boolean,
                    video: Int)
    suspend fun conference(id: Int): Conference?
    suspend fun conference(id: String): Conference?
    suspend fun conferences(): List<Conference>
    suspend fun update(
                    title: String,
                    hour: String,
                    duration: Float,
                    speaker: Int,
                    topic: Int,
                    description: String,
                    streaming: Boolean,
                    video: Int)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}