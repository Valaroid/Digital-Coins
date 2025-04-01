package ir.valaroid.digitalcoins.secondActivity

import android.util.Log
import ir.valaroid.digitalcoins.model.ApiManager
import ir.valaroid.digitalcoins.model.DataAboutCoin
import ir.valaroid.digitalcoins.model.DataAsset
import ir.valaroid.digitalcoins.model.DataChart
import ir.valaroid.digitalcoins.model.DataCoins
import ir.valaroid.digitalcoins.model.MyCallBack
import ir.valaroid.digitalcoins.utils.ALL
import ir.valaroid.digitalcoins.utils.HISTO_DAY
import ir.valaroid.digitalcoins.utils.HISTO_HOUR
import ir.valaroid.digitalcoins.utils.HISTO_MINUTE
import ir.valaroid.digitalcoins.utils.HOUR12
import ir.valaroid.digitalcoins.utils.HOURS24
import ir.valaroid.digitalcoins.utils.KEY_FOR_BUNDLE1
import ir.valaroid.digitalcoins.utils.MONTH
import ir.valaroid.digitalcoins.utils.MONTH3
import ir.valaroid.digitalcoins.utils.WEEK
import ir.valaroid.digitalcoins.utils.YEAR


class SecondPresenter(private val apiManager: ApiManager) : SecondActivityContract.Presenter  {
    private  var view:SecondActivityContract.View?=null

    override fun onAttach(view: SecondActivityContract.View) {
        this.view=view


    }

    override fun onDetach() {
    view=null

    }


    override fun getDataFromFirstPresenter(dataThisCoin : DataCoins.Data) {

        view!!.showDataFromFirstPresenter(dataThisCoin)

    }

    override fun getAboutDataFromFirst(data: DataAboutCoin) {

        view!!.showAboutDataFromFirst(data)


    }






    override fun getDataFromChart(fromSymbol : String,time:String) {

        var aggregate : Int =1
        var limit :Int = 10
        var histoPeriod : String =""

        when(time){

            HOUR12 -> {
                histoPeriod = HISTO_MINUTE
                limit = 60
                aggregate = 12
            }

            HOURS24 -> {
                histoPeriod = HISTO_HOUR
                limit = 24
            }

            MONTH -> {
                histoPeriod = HISTO_DAY
                limit = 30
            }

            MONTH3 -> {
                histoPeriod = HISTO_DAY
                limit = 90
            }

            WEEK -> {
                histoPeriod = HISTO_HOUR
                aggregate = 6
            }

            YEAR -> {
                histoPeriod = HISTO_DAY
                aggregate = 13
            }

            ALL -> {
                histoPeriod = HISTO_DAY
                aggregate = 30
                limit = 2000
            }



        }


        apiManager.getChartData(fsym = fromSymbol, period = histoPeriod,
            aggregate = aggregate, limit = limit, myCallBack = object : MyCallBack<Pair<List<DataChart.Data>,DataChart.Data?>> {
                override fun onSuccess(data: Pair<List<DataChart.Data>, DataChart.Data?>) {

                view!!.showDataFromChart(data.first,data.second)


                }



                override fun onFailure(message: String) {
                    view!!.createToast("Refer to this page again Please")

                }

            })



    }


}