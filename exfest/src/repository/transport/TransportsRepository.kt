package repository.transport

import model.transport.Transport

interface TransportsRepository {
    suspend fun add(name: String,
                    number: Int,
                    cost: Float,
                    shared: Boolean,
                    dateRequest: String,
                    dateArrive: String,
                    dateExit: String)
    suspend fun transport(id: Int): Transport?
    suspend fun transport(id: String): Transport?
    suspend fun transports(): List<Transport>
    suspend fun update(
        name: String,
        number: Int,
        cost: Float,
        shared: Boolean,
        dateRequest: String,
        dateArrive: String,
        dateExit: String)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}