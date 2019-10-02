package repository.conferences

import model.conference.Conference
import model.conference.Conferences
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ConferencesImplementation : ConferencesRepository {

    private fun toConference(row: ResultRow): Conference =
        Conference(
            id = row[Conferences.id],
            title = row[Conferences.title],
            hour = row[Conferences.hour],
            duration = row[Conferences.duration],
            speaker = row[Conferences.speaker],
            topic = row[Conferences.topic],
            description = row[Conferences.description],
            streaming = row[Conferences.streaming],
            video =  row[Conferences.video]
        )
    override suspend fun add(title: String,
                             hour: String,
                             duration: Float,
                             speaker: Int,
                             topic: Int,
                             description: String,
                             streaming: Boolean,
                             video: Int) {
        transaction {
            Conferences.insert {
                it [this.title] = title
                it [this.hour] = hour
                it [this.duration] = duration
                it [this.speaker] = speaker
                it [this.topic] = topic
                it [this.description] = description
                it [this.streaming] = streaming
                it [this.video] =  video
            }
        }
    }

    override suspend fun conference(id: Int): Conference? = dbQuery{
        Conferences.select {
            (Conferences.id eq id)
        }.mapNotNull { toConference(it) }.singleOrNull()
    }

    override suspend fun conference(id: String): Conference? {
        return conference(id.toInt())
    }

    override suspend fun conferences(): List<Conference> = dbQuery {
        Conferences.selectAll().map { toConference(it) }
    }

    override suspend fun update(
                             title: String,
                             hour: String,
                             duration: Float,
                             speaker: Int,
                             topic: Int,
                             description: String,
                             streaming: Boolean,
                             video: Int) {
        transaction {
            Conferences.update {
                it [this.title] = title
                it [this.hour] = hour
                it [this.duration] = duration
                it [this.speaker] = speaker
                it [this.topic] = topic
                it [this.description] = description
                it [this.streaming] = streaming
                it [this.video] =  video
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (conference(id) == null) {
            throw  IllegalArgumentException("No phrase found for id ${conference(id)}")
        }
        return dbQuery {
            Conferences.deleteWhere { Conferences.id eq id } > 0
        }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        conferences().map { remove(it.id) }
    }

}