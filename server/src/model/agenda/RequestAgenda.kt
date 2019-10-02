package model.agenda

data class RequestAgenda(
                  val day: String,
                  val speaker: Int,
                  val conference: Int,
                  val place: Int,
                  val catering: Int,
                  val service: Int,
                  val year: Int)