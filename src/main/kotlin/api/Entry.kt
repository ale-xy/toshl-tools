package api

data class Entry (

    val id : Int,
    val amount : Double,
    val currency : Currency,
    val date : String,
    val desc : String,
    val account : String,
    val category : String,
    val tags : List<Int>,
    val location : Location,
    val modified : String,
    val repeat : Repeat,
    val transaction : Transaction,
    val images : List<Images>,
    val reminders : List<Reminders>,
    val completed : Boolean
)

data class Images (

    val id : Int,
    val path : String,
    val status : String
)

data class Reminders (

    val period : String,
    val number : Int,
    val at : String
)

data class Location (

    val id : Int,
    val latitude : Double,
    val longitude : Double
)

data class Repeat (

    val id : Int,
    val frequency : String,
    val interval : Int,
    val start : String,
    val count : Int,
    val iteration : Int,
    val template : Boolean
)

data class Transaction (

    val id : Int,
    val account : Int,
    val currency : Currency
)