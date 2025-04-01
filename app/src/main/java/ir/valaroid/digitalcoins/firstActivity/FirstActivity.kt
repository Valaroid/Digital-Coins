package ir.valaroid.digitalcoins.firstActivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import ir.valaroid.digitalcoins.databinding.ActivityFirstBinding
import ir.valaroid.digitalcoins.model.ApiManager
import ir.valaroid.digitalcoins.model.DataAboutCoin
import ir.valaroid.digitalcoins.model.DataAsset
import ir.valaroid.digitalcoins.model.DataCoins
import ir.valaroid.digitalcoins.secondActivity.SecondActivity
import ir.valaroid.digitalcoins.utils.HISTO_HOUR
import ir.valaroid.digitalcoins.utils.KEY_FOR_BUNDLE1
import ir.valaroid.digitalcoins.utils.KEY_FOR_BUNDLE2
import ir.valaroid.digitalcoins.utils.KEY_FOR_INTENT1

class FirstActivity : AppCompatActivity(), FirstActivityContract.View, CoinsEvent {
    private lateinit var binding: ActivityFirstBinding
    private lateinit var presenter: FirstActivityContract.Presenter
    private var dataNews: ArrayList<String>? = null
    private var dataWebsite: ArrayList<String>? = null
    private var randomNumber: Int = 0
    private lateinit var dataFromAsset: DataAsset
    private lateinit var map: Map<String, DataAboutCoin?>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = FirstPresenter(ApiManager())
        presenter.onAttach(this)


        //-----------------------------------------------------------
        val fileInString = applicationContext.assets
            .open("currencyinfo.json")
            .bufferedReader()
            .use { it.readText() }

        val gson = Gson()
        dataFromAsset = gson.fromJson(fileInString, DataAsset::class.java)

        map = presenter.sendDataFromAsset(dataFromAsset)

        //-------------------------------------------------------------


        binding.moduleNews.txtNewsFirstActivity.setOnClickListener {

            if (dataNews != null) {
                setNews()
            }


        }

        binding.moduleNews.imgNewsFirstActivity.setOnClickListener {

            openWebsiteNews()
        }

        binding.moduleRecyclerView.btnMoreFirstActivity.setOnClickListener {
            val url = "https://www.livecoinwatch.com/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)

        }

        binding.swipeRefreshFirstActivity.setOnRefreshListener {

            refreshFullPage()

            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefreshFirstActivity.isRefreshing = false
            }, 400)


        }




    }


    override fun onResume() {
        super.onResume()
        refreshFullPage()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }


    override fun showTitleOfNews(data: ArrayList<String>) {
        dataNews = data

        setNews()

    }

    override fun showUrlOfNews(data: ArrayList<String>) {
        dataWebsite = data

    }

    override fun createToast(message: String) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }

    override fun showDataCoins(data: ArrayList<DataCoins.Data>) {

        binding.moduleRecyclerView.recyclerViewFirstActivity.adapter = CoinsAdapter(data, this)
        binding.moduleRecyclerView.recyclerViewFirstActivity.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }

    private fun setNews() {

        randomNumber = (0..49).random()

        if (dataNews != null) {
            binding.moduleNews.txtNewsFirstActivity.text = dataNews!![randomNumber]
        }


    }

    private fun openWebsiteNews() {
        if (dataWebsite != null) {
            val url = dataWebsite!![randomNumber]
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }


    }

    private fun refreshFullPage() {
        presenter.getDataNews()
        presenter.getDataCoins()

    }

    override fun onCoinClicked(dataCoin: DataCoins.Data) {
        val intent = Intent(this, SecondActivity::class.java)

        val bundle = Bundle()
        bundle.putParcelable(KEY_FOR_BUNDLE1, dataCoin)


        if (map.containsKey(dataCoin.coinInfo.name)) {
            bundle.putParcelable(KEY_FOR_BUNDLE2, map[dataCoin.coinInfo.name])
            Log.v("shervin", dataCoin.coinInfo.name)
        }

        Log.v("testBundle", bundle.toString())
        intent.putExtra(KEY_FOR_INTENT1, bundle)

        startActivity(intent)

    }


}