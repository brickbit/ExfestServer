package model.catering

data class RequestCatering(
    val name: String,
    val cost: Float,
    val mandatory: Boolean,
    val foodRestriction: Int,
    val numberFoodRestriction: Int,
    val numberDiner: Int,
    val dateService: String,
    val dateRequest: String)