package ir.valaroid.digitalcoins.model

import ir.valaroid.digitalcoins.utils.API_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

// vazayefe joghde harry

interface ApiService {

    @Headers(API_KEY)
    @GET("v2/news/")
    fun getTopNews(@Query("sortOrder") sortOrder: String = "popular"): Call<DataNews>

    @Headers(API_KEY)
    @GET("top/totalvolfull")
    fun getTopCoins(
        @Query("tsym") toSymbol: String = "USD",
        @Query("limit") limit: Int = 10
    ): Call<DataCoins>


    @Headers(API_KEY)
    @GET("{period}")

    fun getChartData(
        @Path("period") period: String,
        @Query("fsym") fsym: String,
        @Query("aggregate") aggregate: Int,
        @Query("limit") limit: Int,
        @Query("tsym") tsym: String = "USD"
    ): Call<DataChart>


}