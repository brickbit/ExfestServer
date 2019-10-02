package repository.speakers

import model.speaker.Speaker
import model.speaker.Speakers
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class SpeakersImplementation : SpeakersRepository {
    private fun toSpeaker(row: ResultRow): Speaker =
        Speaker(
            id = row[Speakers.id],
            name = row[Speakers.name],
            surname = row[Speakers.surname],
            genre = row[Speakers.genre],
            transport = row[Speakers.transport],
            foodRestriction = row[Speakers.foodRestriction],
            merchandising = row[Speakers.merchandising],
            moreInfo = row[Speakers.moreInfo],
            children =  row[Speakers.children],
            comeChildren =  row[Speakers.comeChildren],
            nChildren =  row[Speakers.nChildren],
            ageChildren =  row[Speakers.ageChildren],
            hotel =  row[Speakers.hotel],
            cost =  row[Speakers.cost],
            numberVisit =  row[Speakers.numberVisit],
            image =  row[Speakers.image],
            company =  row[Speakers.company],
            rating =  row[Speakers.rating],
            date =  row[Speakers.date]

        )
    override suspend fun add(name: String,
                             surname: String,
                             genre: String,
                             transport: Int,
                             foodRestriction: Int,
                             merchandising: Int,
                             moreInfo: String,
                             children: Boolean,
                             comeChildren: Boolean,
                             nChildren: Int,
                             ageChildren: Int,
                             hotel: Int,
                             cost: Float,
                             numberVisit: Int,
                             image: String,
                             company: String,
                             rating: Int,
                             date: String
    ) {
        transaction {
            Speakers.insert {
                it [this.name] = name
                it [this.surname] = surname
                it [this.genre] = genre
                it [this.transport] = transport
                it [this.foodRestriction] = foodRestriction
                it [this.merchandising] = merchandising
                it [this.moreInfo] =  moreInfo
                it [this.children] = children
                it [this.comeChildren] =  comeChildren
                it [this.nChildren] = nChildren
                it [this.ageChildren] =  ageChildren
                it [this.hotel] = hotel
                it [this.cost] =  cost
                it [this.numberVisit] = numberVisit
                it [this.image] =  image
                it [this.company] = company
                it [this.rating] =  rating
                it [this.date] =  date

            }
        }
    }

    override suspend fun speaker(id: Int): Speaker? = dbQuery {
        Speakers.select {
            (Speakers.id eq id)
        }.mapNotNull { toSpeaker(it) }.singleOrNull()
    }

    override suspend fun speaker(id: String): Speaker? {
        return speaker(id.toInt())
    }

    override suspend fun speakers(): List<Speaker> = dbQuery {
        Speakers.selectAll().map { toSpeaker(it) }
    }

    override suspend fun update(
                             name: String,
                             surname: String,
                             genre: String,
                             transport: Int,
                             foodRestriction: Int,
                             merchandising: Int,
                             moreInfo: String,
                             children: Boolean,
                             comeChildren: Boolean,
                             nChildren: Int,
                             ageChildren: Int,
                             hotel: Int,
                             cost: Float,
                             numberVisit: Int,
                             image: String,
                             company: String,
                             rating: Int,
                             date: String
    ) {
        transaction {
            Speakers.update {
                it [this.name] = name
                it [this.surname] = surname
                it [this.genre] = genre
                it [this.transport] = transport
                it [this.foodRestriction] = foodRestriction
                it [this.merchandising] = merchandising
                it [this.moreInfo] =  moreInfo
                it [this.children] = children
                it [this.comeChildren] =  comeChildren
                it [this.nChildren] = nChildren
                it [this.ageChildren] =  ageChildren
                it [this.hotel] = hotel
                it [this.cost] =  cost
                it [this.numberVisit] = numberVisit
                it [this.image] =  image
                it [this.company] = company
                it [this.rating] =  rating
                it [this.date] =  date

            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (speaker(id) == null) {
            throw  IllegalArgumentException("No phrase found for id ${speaker(id)}")
        }
        return dbQuery {
            Speakers.deleteWhere { Speakers.id eq id } > 0
        }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        speakers().map { remove(it.id) }
    }

}