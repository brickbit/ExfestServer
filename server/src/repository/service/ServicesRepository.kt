package repository.service

import model.service.Service

interface ServicesRepository {
    suspend fun add(name: String,
                    cost: Float,
                    description: String,
                    granted: Boolean,
                    company: String,
                    rating: Int)
    suspend fun service(id: Int): Service?
    suspend fun service(id: String): Service?
    suspend fun services(): List<Service>
    suspend fun update(
                    name: String,
                    cost: Float,
                    description: String,
                    granted: Boolean,
                    company: String,
                    rating: Int)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}