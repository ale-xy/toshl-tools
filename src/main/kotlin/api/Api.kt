package api

import retrofit2.Call
import retrofit2.http.*

interface Toshl {
    @GET("/accounts")
    fun accounts(): Call<List<Account>>

    @GET("/bank/connections")
    fun bankConnections(): Call<List<BankConnection>>

    @GET("/entries")
    fun entries(@Query("accounts") accounts: String,
                @Query("from") from: String,
                @Query("to") to: String,
                @Query("page_limit") pageLimit: Int = 500
    ): Call<List<Entry>>

    @POST("/entries")
    fun createEntry(@Body entry:Entry): Call<Entry>

    @DELETE("/entries/{id}")
    fun deleteEntry(@Path("id") id:Int): Call<Entry>

}