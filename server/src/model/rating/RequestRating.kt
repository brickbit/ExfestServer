package model.rating

data class RequestRating(
                  val rate: Int,
                  val opinion: String,
                  val service: Int,
                  val catering: Int,
                  val hotel: Int)