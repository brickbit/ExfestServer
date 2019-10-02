package repository.attendee

import model.attendee.Attendee

interface AttendeesRepository {
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
                    rating: Int,
                    dateRequestTicket: String,
                    dateGrantTicket: String,
                    datePayedTicket: String,
                    timesExpiredTicket: Int,
                    timesAbsent: Int)
    suspend fun attendee(id: Int): Attendee?
    suspend fun attendee(id: String): Attendee?
    suspend fun attendees(): List<Attendee>
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
                    rating: Int,
                    dateRequestTicket: String,
                    dateGrantTicket: String,
                    datePayedTicket: String,
                    timesExpiredTicket: Int,
                    timesAbsent: Int)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}