package ir.valaroid.digitalcoins.firstActivity

import android.os.Bundle
import ir.valaroid.digitalcoins.model.DataAboutCoin
import ir.valaroid.digitalcoins.model.DataAsset
import ir.valaroid.digitalcoins.model.DataCoins

interface FirstActivityContract {

    interface View{
        fun showTitleOfNews(data: ArrayList<String>)
        fun showUrlOfNews(data:ArrayList<String>)
        fun createToast(message : String)
        fun showDataCoins(data: ArrayList<DataCoins.Data>)


    }


    interface Presenter{

        fun onAttach(view:View)
        fun onDetach()

        fun getDataNews()
        fun getDataCoins()
        fun sendDataToActivitySecond(dataCoin: DataCoins.Data): Bundle

        fun sendDataFromAsset(data : DataAsset) : Map<String,DataAboutCoin?>


    }


}