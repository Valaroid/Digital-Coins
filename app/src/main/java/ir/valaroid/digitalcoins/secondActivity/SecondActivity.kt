package ir.valaroid.digitalcoins.secondActivity


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import ir.valaroid.digitalcoins.R
import ir.valaroid.digitalcoins.databinding.ActivitySecondBinding
import ir.valaroid.digitalcoins.model.ApiManager
import ir.valaroid.digitalcoins.model.DataAboutCoin
import ir.valaroid.digitalcoins.model.DataAsset
import ir.valaroid.digitalcoins.model.DataChart
import ir.valaroid.digitalcoins.model.DataCoins
import ir.valaroid.digitalcoins.utils.ALL
import ir.valaroid.digitalcoins.utils.BASE_URL_TWITTER
import ir.valaroid.digitalcoins.utils.HISTO_HOUR
import ir.valaroid.digitalcoins.utils.HOUR12
import ir.valaroid.digitalcoins.utils.HOURS24
import ir.valaroid.digitalcoins.utils.KEY_FOR_BUNDLE1
import ir.valaroid.digitalcoins.utils.KEY_FOR_BUNDLE2

import ir.valaroid.digitalcoins.utils.KEY_FOR_INTENT1
import ir.valaroid.digitalcoins.utils.KEY_FOR_INTENT2
import ir.valaroid.digitalcoins.utils.MONTH
import ir.valaroid.digitalcoins.utils.MONTH3
import ir.valaroid.digitalcoins.utils.WEEK
import ir.valaroid.digitalcoins.utils.YEAR

class SecondActivity : AppCompatActivity(), SecondActivityContract.View {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var presenter: SecondPresenter
    private lateinit var dataThisCoin: DataCoins.Data

    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = SecondPresenter(ApiManager())
        presenter.onAttach(this)





        val intent = intent.getBundleExtra(KEY_FOR_INTENT1)!!

        dataThisCoin = intent.getParcelable<DataCoins.Data>(KEY_FOR_BUNDLE1)!!

            presenter.getDataFromFirstPresenter(dataThisCoin)
            binding.moduleToolBar.toolBar.title = dataThisCoin.coinInfo.fullName

            presenter.getDataFromChart(dataThisCoin.coinInfo.name, HOUR12)


        if (intent.containsKey(KEY_FOR_BUNDLE2)) {
            val dataAboutCoin = intent.getParcelable<DataAboutCoin>(KEY_FOR_BUNDLE2)
            if (dataAboutCoin != null) {

                presenter.getAboutDataFromFirst(dataAboutCoin)

            }


        }


        binding.moduleChart.radioGroupSecondActivity.setOnCheckedChangeListener { _, checkedId ->

            when (checkedId) {

                R.id.btn12H_secondActivity -> {

                    presenter.getDataFromChart(dataThisCoin.coinInfo.name, HOUR12)


                }


                R.id.btn1D_secondActivity -> {
                    presenter.getDataFromChart(dataThisCoin.coinInfo.name, HOURS24)

                }


                R.id.btn1W_secondActivity -> {
                    presenter.getDataFromChart(dataThisCoin.coinInfo.name, WEEK)

                }


                R.id.btn1M_secondActivity -> {
                    presenter.getDataFromChart(dataThisCoin.coinInfo.name, MONTH)

                }


                R.id.btn3M_secondActivity -> {
                    presenter.getDataFromChart(dataThisCoin.coinInfo.name, MONTH3)

                }


                R.id.btn1Y_secondActivity -> {
                    presenter.getDataFromChart(dataThisCoin.coinInfo.name, YEAR)

                }


                R.id.btnAll_secondActivity -> {
                    presenter.getDataFromChart(dataThisCoin.coinInfo.name, ALL)

                }


            }


        }

        binding.moduleChart.sparkSecondActivity.setScrubListener {

            if (it==null){
                binding.moduleChart.txtPriceSecondActivity.text=dataThisCoin.dISPLAY.uSD.pRICE
            }else{
                binding.moduleChart.txtPriceSecondActivity.text= "$ "+(it as DataChart.Data).close.toString()

            }

        }


    }


    override fun showDataFromFirstPresenter(dataCoin: DataCoins.Data) {

        binding.moduleChart.txtPriceSecondActivity.text = dataCoin.dISPLAY.uSD.pRICE
        binding.moduleChart.txtCapSecondActivity.text = dataCoin.dISPLAY.uSD.mKTCAP

        topOfPage(dataCoin)

        //todo namayesh tedad argham baded ashar
        binding.moduleStatistics.txtOpen.text = dataCoin.dISPLAY.uSD.oPEN24HOUR
        binding.moduleStatistics.txtHigh.text = dataCoin.dISPLAY.uSD.hIGH24HOUR
        binding.moduleStatistics.txtLow.text = dataCoin.dISPLAY.uSD.lOW24HOUR
        binding.moduleStatistics.txtCap.text = dataCoin.dISPLAY.uSD.mKTCAP
        binding.moduleStatistics.txtChange.text = dataCoin.dISPLAY.uSD.cHANGE24HOUR
        binding.moduleStatistics.txtSupply.text = dataCoin.dISPLAY.uSD.sUPPLY
        binding.moduleStatistics.txtTotalVolume.text = dataCoin.dISPLAY.uSD.vOLUME24HOUR
        binding.moduleStatistics.txtAlgorithm.text = dataCoin.coinInfo.algorithm

    }

    @SuppressLint("SetTextI18n")
    override fun showAboutDataFromFirst(data: DataAboutCoin) {
        val web = data.websSite

        if (web?.isNotEmpty() == true) {
            binding.moduleAbout.txtWebsiteSecondActivity.text = web
        }

        binding.moduleAbout.txtWebsiteSecondActivity.setOnClickListener {
            if (web?.isNotEmpty() == true) {
                openWebsite(web!!)
            }
        }

        val twit = data.twitter
        if (twit?.isNotEmpty() == true) {
            binding.moduleAbout.txtTwitterSecondActivity.text = "@" + twit
        }

        binding.moduleAbout.txtTwitterSecondActivity.setOnClickListener {
            if (twit?.isNotEmpty() == true) {
                openWebsite(BASE_URL_TWITTER + twit)
            }

        }

        val red = data.reddit
        if (red?.isNotEmpty() == true) {
            binding.moduleAbout.txtRedditSecondActivity.text = red
        }
        binding.moduleAbout.txtRedditSecondActivity.setOnClickListener {
            if (red?.isNotEmpty() == true) {
                openWebsite(red)
            }

        }

        val git = data.github
        if (git?.isNotEmpty() == true) {
            binding.moduleAbout.txtGitHubSecondActivity.text = git
        }


        binding.moduleAbout.txtGitHubSecondActivity.setOnClickListener {
            if (git?.isNotEmpty() == true) {
                openWebsite(git)
            }
        }

        binding.moduleAbout.txtDetailSecondActivity.text = data.dec


    }


    override fun showDataFromChart(data: List<DataChart.Data>, baseLine: DataChart.Data?) {

       binding.moduleChart.sparkSecondActivity.adapter=ChartAdapter(data,baseLine?.open.toString())


    }

    override fun createToast(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }


    private fun topOfPage(dataCoin: DataCoins.Data) {

        val changePrice = dataCoin.rAW.uSD.cHANGE24HOUR
        val percentPrice = changePrice / 100
        val indexOf = percentPrice.toString().indexOf('.')
        val showPrice = percentPrice.toString().substring(0, indexOf + 4)

        if (changePrice < 0) {
            binding.moduleChart.txtChangeSecondActivity.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorLoss
                )
            )
            binding.moduleChart.txtUpOrDownSecondActivity.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorLoss
                )
            )

            binding.moduleChart.sparkSecondActivity.lineColor= ContextCompat.getColor(
                binding.root.context,
                R.color.colorLoss
            )


            binding.moduleChart.txtChangeSecondActivity.text = "$showPrice%"
            binding.moduleChart.txtUpOrDownSecondActivity.text = "▼"


        } else if (changePrice > 0) {

            binding.moduleChart.txtChangeSecondActivity.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorGain
                )
            )

            binding.moduleChart.txtUpOrDownSecondActivity.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorGain
                )
            )


            binding.moduleChart.sparkSecondActivity.lineColor= ContextCompat.getColor(
                binding.root.context,
                R.color.colorGain
            )

            binding.moduleChart.txtChangeSecondActivity.text = "+$showPrice% "
            binding.moduleChart.txtUpOrDownSecondActivity.text = "▲"

        } else {

            binding.moduleChart.txtChangeSecondActivity.text = "0"
        }
    }

    private fun openWebsite(url: String) {

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }


}