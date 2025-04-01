package ir.valaroid.digitalcoins.firstActivity

import android.os.Bundle
import android.util.Log
import ir.valaroid.digitalcoins.model.ApiManager
import ir.valaroid.digitalcoins.model.DataAboutCoin
import ir.valaroid.digitalcoins.model.DataAsset
import ir.valaroid.digitalcoins.model.DataCoins
import ir.valaroid.digitalcoins.model.MyCallBack
import ir.valaroid.digitalcoins.utils.KEY_FOR_BUNDLE1

class FirstPresenter( private val apiManger : ApiManager) : FirstActivityContract.Presenter {
    private var view: FirstActivityContract.View? = null


    override fun onAttach(view: FirstActivityContract.View) {
        this.view = view
        getDataNews()
        getDataCoins()
    }

    override fun onDetach() {
        view = null

    }

    override fun getDataNews() {

        apiManger.getTopNews(object : MyCallBack<ArrayList<Pair<String, String>>> {


            override fun onSuccess(data: ArrayList<Pair<String, String>>) {
                Log.v("giveDataNews", data.toString())


                val arrayListOfTitleNews: ArrayList<String> = arrayListOf()

                data.forEach {
                    arrayListOfTitleNews.add(it.first)
                }

                view!!.showTitleOfNews(arrayListOfTitleNews)


                val arrayListOfUrlNews: ArrayList<String> = arrayListOf()

                data.forEach {

                    arrayListOfUrlNews.add(it.second)
                }

                view!!.showUrlOfNews(arrayListOfUrlNews)


            }


            override fun onFailure(message: String) {

                Log.v("giveDataNews", message)

                //view!!.createToast("An error has occurred on the server! Please refresh the page")

            }


        })

    }


    //ino baraye activity dovom niyaz darim
    override fun getDataCoins() {
        apiManger.getTopCoins(object : MyCallBack<ArrayList<DataCoins.Data>> {

            override fun onSuccess(data: ArrayList<DataCoins.Data>) {
                Log.v("giveDataCoin", data.toString())
                view!!.showDataCoins(data)

            }

            override fun onFailure(message: String) {
                Log.v("giveDataCoin", message)

                view!!.createToast("An error has occurred on the server! Please refresh the page")

            }


        })


    }

    override fun sendDataToActivitySecond(dataCoin: DataCoins.Data) :Bundle {

        val bundle = Bundle()
        bundle.putParcelable(KEY_FOR_BUNDLE1,dataCoin)
        return bundle
    }

    override fun sendDataFromAsset(data: DataAsset): Map<String, DataAboutCoin?> {

        val map= mutableMapOf<String,DataAboutCoin>()

        data.forEach {
            map[it.currencyName] = DataAboutCoin(it.info.web,it.info.twt,it.info.reddit,it.info.github,it.info.desc)

        }

        return map


    }


}