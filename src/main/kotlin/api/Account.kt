package api

data class Account(
    val balance: Double,
    val currency: Currency,
    val goal: Goal,
    val id: String,
    val initial_balance: Double,
    val median: Median,
    val modified: String,
    val name: String,
    val order: Int,
    val status: String
)

data class Median(
    val expenses: Double,
    val incomes: Double
)

data class Goal(
    val amount: Double,
    val end: String,
    val start: String
)