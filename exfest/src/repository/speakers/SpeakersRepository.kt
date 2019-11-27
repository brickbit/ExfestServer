package repository.speakers

import model.speaker.Speaker

interface SpeakersRepository {
    suspend fun add(name: String,
                    surname: String,
                    foodRestriction: String,
                    genre: String,
                    transport: Int,
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
                    date: String,
                    email: String)
    suspend fun speaker(id: Int): Speaker?
    suspend fun speaker(id: String): Speaker?
    suspend fun speakers(): List<Speaker>
    suspend fun update(
                    name: String,
                    surname: String,
                    foodRestriction: String,
                    genre: String,
                    transport: Int,
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
                    date: String,
                    email: String)
    suspend fun remove(id: Int): Boolean
    suspend fun remove(id: String): Boolean
    suspend fun clear()
}