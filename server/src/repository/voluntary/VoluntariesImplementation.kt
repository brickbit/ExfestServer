package repository.voluntary

import model.voluntary.Voluntaries
import model.voluntary.Voluntary
import repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class VoluntariesImplementation : VoluntariesRepository {
    private fun toVoluntary(row: ResultRow): Voluntary =
        Voluntary(
            id = row[Voluntaries.id],
            name = row[Voluntaries.name],
            foodRestriction = row[Voluntaries.foodRestriction],
            surname = row[Voluntaries.surname],
            genre = row[Voluntaries.genre],
            transport = row[Voluntaries.transport],
            merchandising = row[Voluntaries.merchandising],
            moreInfo = row[Voluntaries.moreInfo],
            children =  row[Voluntaries.children],
            comeChildren =  row[Voluntaries.comeChildren],
            nChildren =  row[Voluntaries.nChildren],
            ageChildren =  row[Voluntaries.ageChildren],
            hotel =  row[Voluntaries.hotel],
            priceTicket =  row[Voluntaries.priceTicket],
            numberVisit =  row[Voluntaries.numberVisit],
            image =  row[Voluntaries.image],
            company = row[Voluntaries.company],
            dateRequestTicket = row[Voluntaries.dateRequestTicket],
            dateGrantTicket = row[Voluntaries.dateGrantTicket],
            datePayedTicket = row[Voluntaries.datePayedTicket],
            timesExpiredTicket = row[Voluntaries.timesExpiredTicket],
            timesAbsent = row[Voluntaries.timesAbsent],
            gdg = row[Voluntaries.gdg],
            cost = row[Voluntaries.cost],
            email = row[Voluntaries.email]
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
        dateRequestTicket: String,
        dateGrantTicket: String,
        datePayedTicket: String,
        timesExpiredTicket: Int,
        timesAbsent: Int,
        gdg: String,
        cost: Float,
        email: String
    ) {
        transaction {
            Voluntaries.insert {
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
                it [this.dateRequestTicket] = dateRequestTicket
                it [this.dateGrantTicket] = dateGrantTicket
                it [this.datePayedTicket] = datePayedTicket
                it [this.timesExpiredTicket] = timesExpiredTicket
                it [this.timesAbsent] = timesAbsent
                it [this.gdg] = gdg
                it [this.cost] = cost
                it [this.email] = email
            }
        }
    }

    override suspend fun voluntary(id: Int): Voluntary? = dbQuery {
        Voluntaries.select {
            (Voluntaries.id eq id)
        }.mapNotNull { toVoluntary(it) }.singleOrNull()
    }

    override suspend fun voluntary(id: String): Voluntary? {
        return voluntary(id.toInt())
    }

    override suspend fun voluntaries(): List<Voluntary> = dbQuery {
        Voluntaries.selectAll().map { toVoluntary(it) }
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
        dateRequestTicket: String,
        dateGrantTicket: String,
        datePayedTicket: String,
        timesExpiredTicket: Int,
        timesAbsent: Int,
        gdg: String,
        cost: Float,
        email: String
    ) {
        transaction {
            Voluntaries.update {
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
                it [this.dateRequestTicket] = dateRequestTicket
                it [this.dateGrantTicket] = dateGrantTicket
                it [this.datePayedTicket] = datePayedTicket
                it [this.timesExpiredTicket] = timesExpiredTicket
                it [this.timesAbsent] = timesAbsent
                it [this.gdg] = gdg
                it [this.cost] = cost
                it [this.email] = email
            }
        }
    }

    override suspend fun remove(id: Int): Boolean {
        if (voluntary(id) == null) {
            throw  IllegalArgumentException("No phrase found for id ${voluntary(id)}")
        }
        return dbQuery {
            Voluntaries.deleteWhere { Voluntaries.id eq id } > 0
        }    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        voluntaries().map { remove(it.id) }
    }

}