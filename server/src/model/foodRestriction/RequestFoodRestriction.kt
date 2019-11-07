package model.foodRestriction

data class RequestFoodRestriction(
    val name: String,
    val idAttendee: Int,
    val idOrganizer: Int,
    val idSpeaker: Int,
    val idVoluntary: Int)