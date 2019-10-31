package model.service

data class RequestService(
                   val name: String,
                   val cost: Float,
                   val description: String,
                   val granted: Boolean,
                   val company: String)