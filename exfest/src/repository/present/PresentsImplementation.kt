package repository.present

import model.present.Present
import model.present.Presents
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PresentsImplementation : PresentsRepository {

    private fun toPresent(row: ResultRow): Present =
        Present(
            id = row[Presents.id],
            name = row[Presents.name],
            cost = row[Presents.cost],
            description = row[Presents.description],
            speaker = row[Presents.speaker],
            voluntary = row[Presents.voluntary],
            attendee = row[Presents.attendee],
            granted = row[Presents.granted],
            partner =  row[Presents.partner]
        )

    override suspend fun add(
        name: String,
        cost: Float,
        description: String,
        speaker: Int,
        voluntary: Int,
        attendee: Int,
        granted: Boolean,
        partner: Int
    ) {
        transaction {
            Presents.insert {
                it [this.name] = name
                it [this.cost] = cost
                it [this.description] = description
                it [this.speaker] = speaker
                it [this.voluntary] = voluntary
                it [this.attendee] = attendee
                it [this.granted] =  granted
                it [this.partner] = partner
            }
        }
    }

    override suspend fun present(id: Int): Present? = dbQuery {
        Presents.select {
            (Presents.id eq id)
        }.mapNotNull { toPresent(it) }.singleOrNull()
    }

    override suspend fun present(id: String): Present? {
        return present(id.toInt())
    }

    override suspend fun presents(): List<Present> = dbQuery {
        Presents.selectAll().map { toPresent(it) }
    }

    override suspend fun update(
        name: String,
        cost: Float,
        description: String,
        speaker: Int,
        voluntary: Int,
        attendee: Int,
        granted: Boolean,
        partner: Int
    ) {
        transaction {
            Presents.update {
                it [this.name] = name
                it [this.cost] = cost
                it [this.description] = description
                it [this.speaker] = speaker
                it [this.voluntary] = voluntary
                it [this.attendee] = attendee
                it [this.granted] =  granted
                it [this.partner] = partner
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (present(id) == null) {
            throw  IllegalArgumentException("No present found for id ${present(id)}")
        }
        return dbQuery {
            Presents.deleteWhere { Presents.id eq id } > 0
        }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        presents().map { remove(it.id) }    }

}