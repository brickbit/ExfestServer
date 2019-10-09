package repository.publicSpeaker

import model.publicSpeaker.PublicSpeaker
import model.publicSpeaker.PublicSpeakers
import model.speaker.Speakers
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PublicSpeakersImplementation : PublicSpeakersRepository {
    private fun toPublicSpeaker(row: ResultRow): PublicSpeaker =
        PublicSpeaker(
            id = row[PublicSpeakers.id],
            name = row[PublicSpeakers.name],
            surname = row[PublicSpeakers.surname],
            moreInfo = row[PublicSpeakers.moreInfo],
            image =  row[PublicSpeakers.image],
            company =  row[PublicSpeakers.company],
            email = row[PublicSpeakers.email]
        )
    override suspend fun add(
        id: Int,
        name: String,
        surname: String,
        moreInfo: String,
        image: String,
        company: String,
        email: String
    ) {
        transaction {
            PublicSpeakers.insert {
                it [this.id] = id
                it [this.name] = name
                it [this.surname] = surname
                it [this.moreInfo] =  moreInfo
                it [this.image] =  image
                it [this.company] = company
                it [this.email] = email
            }
        }
    }

    override suspend fun publicSpeaker(id: Int): PublicSpeaker? = dbQuery {
        PublicSpeakers.select {
            (PublicSpeakers.id eq id)
        }.mapNotNull { toPublicSpeaker(it) }.singleOrNull()
    }

    override suspend fun publicSpeaker(id: String): PublicSpeaker? {
        return publicSpeaker(id.toInt())
    }

    override suspend fun publicSpeakers(): List<PublicSpeaker> = dbQuery {
        PublicSpeakers.selectAll().map { toPublicSpeaker(it) }
    }

    override suspend fun update(
        id: Int,
        name: String,
        surname: String,
        moreInfo: String,
        image: String,
        company: String,
        email: String
    ) {
        transaction {
            PublicSpeakers.update {
                it [this.id] = id
                it [this.name] = name
                it [this.surname] = surname
                it [this.moreInfo] =  moreInfo
                it [this.image] =  image
                it [this.company] = company
                it [this.email] = email
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (publicSpeaker(id) == null) {
            throw  IllegalArgumentException("No phrase found for id ${publicSpeaker(id)}")
        }
        return dbQuery {
            PublicSpeakers.deleteWhere { PublicSpeakers.id eq id } > 0
        }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        publicSpeakers().map { remove(it.id) }
    }

}