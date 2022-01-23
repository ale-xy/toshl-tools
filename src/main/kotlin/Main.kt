import api.Key
import api.Toshl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.*

fun main(args: Array<String>) {
    ToshlProcessor.process()
}

object ToshlProcessor {
    val httpClient: OkHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor {
            it.proceed(
                it.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer " + Key.key)
                    .build())
        }
        .addInterceptor(HttpLoggingInterceptor {
//            println("OKHTTP: $it")
        }.apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        .build()

    val toshlService = Retrofit.Builder()
        .client(httpClient)
        .baseUrl("https://api.toshl.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Toshl::class.java)

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun process() {

//    val accounts = toshlService.accounts().execute().body() ?: throw NullPointerException("Accounts loading error")
//
//    accounts.sortedBy { it.order }.forEach { println(it) }

        val connections = toshlService.bankConnections().execute().body() ?: throw NullPointerException("Connections loading error")

        connections.forEach { println(it) }

        val accountIdsToGet = connections.find { it.name.contains("Тинькофф") }?.accounts ?:throw NullPointerException("Accounts not found")

        val startDate = LocalDate.parse("2021-01-01")
        val endDate = LocalDate.parse("2022-01-01")

        var date = startDate

        do {
            val periodEndDate = date.plusDays(10)
            val periodStart = dateFormatter.format(date)
            val periodEnd = dateFormatter.format(periodEndDate)
            println("---------- $periodStart to $periodEnd")
            removeDuplicates(periodStart, periodEnd, accountIdsToGet)

            date = periodEndDate.plusDays(1)
        } while (date < endDate)
    }

    private fun removeDuplicates(startDate: String, endDate: String, accountIdsToGet: List<String>) {
        val transactions = toshlService.entries(
            accountIdsToGet.joinToString(","),
            startDate,
            endDate
        ).execute().body() ?: throw NullPointerException("Transactions loading error")

        val duplicates = transactions
            .groupBy {
                with(it) {
                    val absAmount = if (amount > 0) amount else -amount
                    "$date $absAmount ${currency.code} ${desc.lowercase(Locale.getDefault())}"
                }
            }.filter {
                it.value.size > 1
            }.toSortedMap()


//            .groupBy {
//                with(it) {
//                    "$date $amount ${currency.code} ${desc.lowercase(java.util.Locale.getDefault())}"
//                }
//            }.filter {
//                it.value.size > 1
//            }.toSortedMap()


        duplicates.forEach {
            println("${it.key} ---")
            with(it.value) {
                forEach { entry -> println(entry) }

                if (size == 2 && first().amount == -last().amount && !first().desc.contains("перевод", true)) {
//                val delete = it.value.maxByOrNull { entry -> entry.id }
//                    ?: throw NullPointerException("Can't find max transaction id")
//                println("Deleting $delete")
                    println("Delete?")
                    val input = readLine()
                    if (input.equals("y", true)) {
                        forEach { entry ->
                            println("Deleting $entry")
                            toshlService.deleteEntry(entry.id).execute()
                        }
                    }
                }
//                else {
//                    print("Multiple repeating transactions")
//                    readLine()
//                }
            }
        }
    }
}