package api

data class Currency (
	val code : String,
	val rate : Double,
	val fixed : Boolean
)