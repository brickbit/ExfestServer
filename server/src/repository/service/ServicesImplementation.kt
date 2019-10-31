package repository.service

import model.service.Service
import model.service.Services
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ServicesImplementation : ServicesRepository {
    private fun toService(row: ResultRow): Service =
        Service(
            id = row[Services.id],
            name = row[Services.name],
            cost = row[Services.cost],
            description = row[Services.description],
            granted = row[Services.granted],
            company = row[Services.company]
        )

    override suspend fun add(name: String, cost: Float, description: String, granted: Boolean, company: String) {
        transaction {
            Services.insert {
                it [this.name] = name
                it [this.cost] = cost
                it [this.description] = description
                it [this.granted] = granted
                it [this.company] = company
            }
        }
    }

    override suspend fun service(id: Int): Service? = dbQuery {
        Services.select {
            (Services.id eq id)
        }.mapNotNull { toService(it) }.singleOrNull()
    }

    override suspend fun service(id: String): Service? {
        return service(id.toInt())
    }

    override suspend fun services(): List<Service> = dbQuery {
        Services.selectAll().map { toService(it) }
    }

    override suspend fun update(name: String, cost: Float, description: String, granted: Boolean, company: String) {
        transaction {
            Services.update {
                it [this.name] = name
                it [this.cost] = cost
                it [this.description] = description
                it [this.granted] = granted
                it [this.company] = company
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (service(id) == null) {
            throw  IllegalArgumentException("No phrase found for id ${service(id)}")
        }
        return dbQuery {
            Services.deleteWhere { Services.id eq id } > 0
        }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        services().map { remove(it.id) }    }

}