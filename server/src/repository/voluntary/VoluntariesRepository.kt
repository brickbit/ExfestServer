package repository.voluntary

import model.voluntary.Voluntary

interface VoluntariesRepository {
    suspend fun add(name: String,
                    surname: String,
                    genre: String,
                    transport: String,
                    foodRestriction: Int,
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
                    email: String)
    suspend fun voluntary(id: Int): Voluntary?
    suspend fun voluntary(id: String): Voluntary?
    suspend fun voluntaries(): List<Voluntary>
    suspend fun update(
                    name: String,
                    surname: String,
                    genre: String,
                    transport: String,
                    foodRestriction: Int,
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
                    email: String)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}