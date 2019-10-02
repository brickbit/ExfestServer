package repository.topic

import model.topic.Topic
import model.topic.Topics
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class TopicsImplementation : TopicsRepository {
    private fun toTopic(row: ResultRow): Topic =
        Topic(
            id = row[Topics.id],
            name = row[Topics.name],
            image =  row[Topics.image]
        )
    override suspend fun add(name: String, image: String) {
        transaction {
            Topics.insert {
                it [this.name] = name
                it [this.image] = image
            }
        }
    }

    override suspend fun topic(id: Int): Topic? = dbQuery {
        Topics.select {
            (Topics.id eq id)
        }.mapNotNull { toTopic(it) }.singleOrNull()
    }

    override suspend fun topic(id: String): Topic? {
        return topic(id.toInt())
    }

    override suspend fun topics(): List<Topic> = dbQuery {
        Topics.selectAll().map { toTopic(it) }
    }

    override suspend fun update(name: String, image: String) {
        transaction {
            Topics.update {
                it [this.name] = name
                it [this.image] = image
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (topic(id) == null) {
            throw  IllegalArgumentException("No rating found for id ${topic(id)}")
        }
        return dbQuery {
            Topics.deleteWhere { Topics.id eq id } > 0
        }    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        topics().map { remove(it.id) }
    }

}