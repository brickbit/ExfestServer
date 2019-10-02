package repository.present

import model.present.Present

interface PresentsRepository {
    suspend fun add(name: String,
                    cost: Float,
                    description: String,
                    speaker: Int,
                    voluntary: Int,
                    attendee: Int,
                    granted: Boolean,
                    partner: Int)
    suspend fun present(id: Int): Present?
    suspend fun present(id: String): Present?
    suspend fun presents(): List<Present>
    suspend fun update(
                    name: String,
                    cost: Float,
                    description: String,
                    speaker: Int,
                    voluntary: Int,
                    attendee: Int,
                    granted: Boolean,
                    partner: Int)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}