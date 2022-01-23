package api

data class Currency (
	val code : String,
	val rate : Int,
	val fixed : Boolean
)