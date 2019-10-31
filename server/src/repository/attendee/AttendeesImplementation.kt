package repository.attendee

import model.attendee.Attendee
import model.attendee.Attendees
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class AttendeesImplementation : AttendeesRepository {
    private fun toAttendee(row: ResultRow): Attendee =
        Attendee(
            id = row[Attendees.id],
            name = row[Attendees.name],
            surname = row[Attendees.surname],
            foodRestriction = row[Attendees.foodRestriction],
            genre = row[Attendees.genre],
            transport = row[Attendees.transport],
            merchandising = row[Attendees.merchandising],
            moreInfo = row[Attendees.moreInfo],
            children =  row[Attendees.children],
            comeChildren =  row[Attendees.comeChildren],
            nChildren =  row[Attendees.nChildren],
            ageChildren =  row[Attendees.ageChildren],
            hotel =  row[Attendees.hotel],
            priceTicket =  row[Attendees.priceTicket],
            numberVisit =  row[Attendees.numberVisit],
            image =  row[Attendees.image],
            company = row[Attendees.company],
            rating = row[Attendees.rating],
            dateRequestTicket = row[Attendees.dateRequestTicket],
            dateGrantTicket = row[Attendees.dateGrantTicket],
            datePayedTicket = row[Attendees.datePayedTicket],
            timesExpiredTicket = row[Attendees.timesExpiredTicket],
            timesAbsent = row[Attendees.timesAbsent],
            email = row[Attendees.email]
        )
    override suspend fun add(
        name: String,
        surname: String,
        foodRestriction: String,
        genre: String,
        transport: String,
        merchandising: Int,
        moreInfo: String,
        children: Boolean,
        comeChildren: Boolean,
        nChildren: Int,
        ageChildren: Int,
        hotel: Int,
        priceTicket: Float,
        numberVisit: Int,
        image: String,
        company: String,
        rating: Int,
        dateRequestTicket: String,
        dateGrantTicket: String,
        datePayedTicket: String,
        timesExpiredTicket: Int,
        timesAbsent: Int,
        email: String
    ) {
        transaction {
            Attendees.insert {
                it [this.name] = name
                it [this.surname] = surname
                it [this.foodRestriction] = foodRestriction
                it [this.genre] = genre
                it [this.transport] = transport
                it [this.merchandising] = merchandising
                it [this.moreInfo] =  moreInfo
                it [this.children] = children
                it [this.comeChildren] =  comeChildren
                it [this.nChildren] = nChildren
                it [this.ageChildren] =  ageChildren
                it [this.hotel] = hotel
                it [this.priceTicket] =  priceTicket
                it [this.numberVisit] = numberVisit
                it [this.image] =  image
                it [this.company] = company
                it [this.rating] =  rating
                it [this.dateRequestTicket] = dateRequestTicket
                it [this.dateGrantTicket] = dateGrantTicket
                it [this.datePayedTicket] = datePayedTicket
                it [this.timesExpiredTicket] = timesExpiredTicket
                it [this.timesAbsent] = timesAbsent
                it [this.email] = email
            }
        }
    }

    override suspend fun attendee(id: Int): Attendee? = dbQuery {
        Attendees.select {
            (Attendees.id eq id)
        }.mapNotNull { toAttendee(it) }.singleOrNull()
    }

    override suspend fun attendee(id: String): Attendee? {
        return attendee(id.toInt())
    }

    override suspend fun attendees(): List<Attendee> = dbQuery {
        Attendees.selectAll().map { toAttendee(it) }
    }

    override suspend fun update(
        name: String,
        surname: String,
        foodRestriction: String,
        genre: String,
        transport: String,
        merchandising: Int,
        moreInfo: String,
        children: Boolean,
        comeChildren: Boolean,
        nChildren: Int,
        ageChildren: Int,
        hotel: Int,
        priceTicket: Float,
        numberVisit: Int,
        image: String,
        company: String,
        rating: Int,
        dateRequestTicket: String,
        dateGrantTicket: String,
        datePayedTicket: String,
        timesExpiredTicket: Int,
        timesAbsent: Int,
        email: String
    ) {
        transaction {
            Attendees.update {
                it [this.name] = name
                it [this.surname] = surname
                it [this.foodRestriction] = foodRestriction
                it [this.genre] = genre
                it [this.transport] = transport
                it [this.merchandising] = merchandising
                it [this.moreInfo] =  moreInfo
                it [this.children] = children
                it [this.comeChildren] =  comeChildren
                it [this.nChildren] = nChildren
                it [this.ageChildren] =  ageChildren
                it [this.hotel] = hotel
                it [this.priceTicket] =  priceTicket
                it [this.numberVisit] = numberVisit
                it [this.image] =  image
                it [this.company] = company
                it [this.rating] =  rating
                it [this.dateRequestTicket] = dateRequestTicket
                it [this.dateGrantTicket] = dateGrantTicket
                it [this.datePayedTicket] = datePayedTicket
                it [this.timesExpiredTicket] = timesExpiredTicket
                it [this.timesAbsent] = timesAbsent
                it [this.email] = email
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (attendee(id) == null) {
            throw  IllegalArgumentException("No phrase found for id ${attendee(id)}")
        }
        return dbQuery {
            Attendees.deleteWhere { Attendees.id eq id } > 0
        }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        attendees().map { remove(it.id) }
    }

}