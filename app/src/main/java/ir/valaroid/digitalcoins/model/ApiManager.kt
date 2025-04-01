package ir.valaroid.digitalcoins.model

import android.util.Log
import ir.valaroid.digitalcoins.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiManager {

    private val apiService: ApiService

    init {


        val httpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        apiService = retrofit.create(ApiService::class.java)

    }


    fun getTopNews(myCallback: MyCallBack<ArrayList<Pair<String, String>>>) {

        apiService.getTopNews().enqueue(object : Callback<DataNews> {
            override fun onResponse(call: Call<DataNews>, response: Response<DataNews>) {

                val fullData = response.body()!!.data

                Log.v("newsTest", fullData.toString())

                val dataToSend: ArrayList<Pair<String, String>> = arrayListOf()

                fullData.forEach {
                    val pair = Pair(it.title, it.url)
                    dataToSend.add(pair)
                }

                myCallback.onSuccess(dataToSend)

            }


            override fun onFailure(call: Call<DataNews>, t: Throwable) {
                Log.v("newsTest", t.toString())

                myCallback.onFailure(t.toString())

            }


        })


    }

    fun getTopCoins(myCallBack: MyCallBack<ArrayList<DataCoins.Data>>) {

        apiService.getTopCoins().enqueue(object : Callback<DataCoins> {
            override fun onResponse(call: Call<DataCoins>, response: Response<DataCoins>) {
                val fullData = response.body()!!.data
                Log.v("coinsTest", fullData.toString())


                val sendData = arrayListOf<DataCoins.Data>()
                fullData.forEach {
                    if (it.rAW != null && it.dISPLAY != null) {
                        sendData.add(it)
                    }
                }
                myCallBack.onSuccess(sendData)

            }

            override fun onFailure(call: Call<DataCoins>, t: Throwable) {
                Log.v("coinsTest", t.toString())
                myCallBack.onFailure(t.toString())

            }


        })


    }

    fun getChartData(
        myCallBack: MyCallBack<Pair<List<DataChart.Data>, DataChart.Data?>>,
        period: String,
        fsym: String,
        aggregate: Int,
        limit: Int
    ) {

        apiService.getChartData(period = period, fsym = fsym, aggregate = aggregate, limit = limit)
            .enqueue(object : Callback<DataChart> {
                override fun onResponse(call: Call<DataChart>, response: Response<DataChart>) {
                    val fullData = response.body()!!
                    Log.v("myData", fullData.toString())

                    val dataPoints = fullData.data
                    val baseLine = fullData.data.maxByOrNull { it.close.toFloat() }


                    myCallBack.onSuccess(Pair(dataPoints, baseLine))

                }

                override fun onFailure(call: Call<DataChart>, t: Throwable) {

                    Log.v("myData", t.message.toString())
                    myCallBack.onFailure(t.message.toString())

                }


            })

    }


}