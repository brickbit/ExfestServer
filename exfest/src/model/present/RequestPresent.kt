package model.present

data class RequestPresent(
                   val name: String,
                   val cost: Float,
                   val description: String,
                   val speaker: Int,
                   val voluntary: Int,
                   val attendee: Int,
                   val granted: Boolean,
                   val partner: Int)