package model.place

data class RequestPlace(
                 val day: String,
                 val cost: Float,
                 val xCoordinate: Float,
                 val yCoordinate: Float,
                 val description: String,
                 val indication: String,
                 val image: String,
                 val mandatoryCatering: Boolean)