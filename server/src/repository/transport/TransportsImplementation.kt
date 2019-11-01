package repository.transport

import model.transport.Transport
import model.transport.Transports
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class TransportsImplementation : TransportsRepository {
    private fun toTransport(row: ResultRow): Transport =
        Transport(
            id = row[Transports.id],
            name = row[Transports.name],
            number =  row[Transports.number],
            cost = row[Transports.cost],
            shared = row[Transports.shared],
            dateRequest = row[Transports.dateRequest],
            dateExit = row[Transports.dateExit],
            dateArrive = row[Transports.dateArrive]
        )
    override suspend fun add(
        name: String,
        number: Int,
        cost: Float,
        shared: Boolean,
        dateRequest: String,
        dateArrive: String,
        dateExit: String
    ) {
        transaction {
            Transports.insert {
                it [this.name] = name
                it [this.number] = number
                it [this.cost] = cost
                it [this.shared] = shared
                it [this.dateRequest] = dateRequest
                it [this.dateArrive] = dateArrive
                it [this.dateExit] = dateExit
            }
        }
    }

    override suspend fun transport(id: Int): Transport? = dbQuery {
        Transports.select {
            (Transports.id eq id)
        }.mapNotNull { toTransport(it) }.singleOrNull()
    }

    override suspend fun transport(id: String): Transport? {
        return transport(id.toInt())
    }

    override suspend fun transports(): List<Transport>  = dbQuery {
        Transports.selectAll().map { toTransport(it) }
    }

    override suspend fun update(
        name: String,
        number: Int,
        cost: Float,
        shared: Boolean,
        dateRequest: String,
        dateArrive: String,
        dateExit: String
    ) {
        transaction {
            Transports.update {
                it [this.name] = name
                it [this.number] = number
                it [this.cost] = cost
                it [this.shared] = shared
                it [this.dateRequest] = dateRequest
                it [this.dateArrive] = dateArrive
                it [this.dateExit] = dateExit
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (transport(id) == null) {
            throw  IllegalArgumentException("No phrase found for id ${transport(id)}")
        }
        return dbQuery {
            Transports.deleteWhere { Transports.id eq id } > 0
        }    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        transports().map { remove(it.id) }
    }

}