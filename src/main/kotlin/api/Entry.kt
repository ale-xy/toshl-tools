package api

data class Entry (

    val id : Int,
    val amount : Double,
    val currency : Currency,
    val date : String,
    val desc : String,
    val account : Int,
    val category : Int,
    val tags : List<Int>,
    val location : Location,
    val modified : String,
    val repeat : Repeat,
    val transaction : Transaction,
    val images : List<Images>,
    val reminders : List<Reminders>,
    val completed : Boolean
)