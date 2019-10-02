package model.speaker

data class RequestSpeaker(
    val name: String,
    val surname: String,
    val genre: String,
    val transport: Int,
    val foodRestriction: Int,
    val merchandising: Int,
    val moreInfo: String,
    val children: Boolean,
    val comeChildren: Boolean,
    val nChildren: Int,
    val ageChildren: Int,
    val hotel: Int,
    val cost: Float,
    val numberVisit: Int,
    val image: String,
    val company: String,
    val rating: Int,
    val date: String)