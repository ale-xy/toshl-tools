package api

data class Repeat (

	val id : Int,
	val frequency : String,
	val interval : Int,
	val start : String,
	val count : Int,
	val iteration : Int,
	val template : Boolean
)