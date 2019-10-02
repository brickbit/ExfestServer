package repository.merchandising

import model.merchandising.Merchandising

interface MerchandisingsRepository {
    suspend fun add(name: String,
                    size: String,
                    cost: Float,
                    genre: String,
                    customized: Boolean)
    suspend fun merchandising(id: Int): Merchandising?
    suspend fun merchandising(id: String): Merchandising?
    suspend fun merchandisings(): List<Merchandising>
    suspend fun update(
                    name: String,
                    size: String,
                    cost: Float,
                    genre: String,
                    customized: Boolean)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}