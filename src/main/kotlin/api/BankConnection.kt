package api

data class BankConnection(
    val accounts: List<String>,
    val id: String,
    val institution: String,
    val name: String,
    val status: String,
    val unsorted: Int
)