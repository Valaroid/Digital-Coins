package ir.valaroid.digitalcoins.secondActivity

import ir.valaroid.digitalcoins.model.DataAboutCoin
import ir.valaroid.digitalcoins.model.DataAsset
import ir.valaroid.digitalcoins.model.DataChart
import ir.valaroid.digitalcoins.model.DataCoins

interface SecondActivityContract {


    interface View{

        fun showDataFromFirstPresenter(dataCoin: DataCoins.Data)
        fun showAboutDataFromFirst(data: DataAboutCoin)

        fun showDataFromChart(data: List<DataChart.Data>, baseLine : DataChart.Data?)
        fun createToast(message : String)

    }


    interface Presenter{

        fun onAttach(view:View)
        fun onDetach()

        fun getDataFromFirstPresenter(dataThisCoin : DataCoins.Data)
        fun getAboutDataFromFirst(data : DataAboutCoin)

        fun getDataFromChart(fromSymbol : String,time:String)



    }



}