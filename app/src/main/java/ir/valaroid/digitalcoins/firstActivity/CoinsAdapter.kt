package ir.valaroid.digitalcoins.firstActivity

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.valaroid.digitalcoins.R
import ir.valaroid.digitalcoins.databinding.ItemRecyclerBinding
import ir.valaroid.digitalcoins.model.DataCoins
import ir.valaroid.digitalcoins.utils.BASE_URL_IMAGE
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class CoinsAdapter(private val data: ArrayList<DataCoins.Data> , private val coinsEvent: CoinsEvent) :
    RecyclerView.Adapter<CoinsAdapter.ViewCoinHolder>() {
    private lateinit var binding: ItemRecyclerBinding


    inner class ViewCoinHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(dataCoin: DataCoins.Data) {

            Log.v("datacheck", dataCoin.toString())

            binding.txtNameCoinItemRecycler.text = dataCoin.coinInfo.fullName


            //val indexPrice = dataCoin.dISPLAY.uSD.pRICE.indexOf('.')
            binding.txtPriceCoinItemRecycler.text =
                dataCoin.dISPLAY.uSD.pRICE



            binding.txtMCapCoinItemRecycler.text = dataCoin.dISPLAY.uSD.mKTCAP

            //for change price
            val changePrice = dataCoin.rAW.uSD.cHANGE24HOUR
            val percentPrice = changePrice / 100
            val indexOf = percentPrice.toString().indexOf('.')
            val showPrice = percentPrice.toString().substring(0, indexOf + 4)
            if (changePrice < 0) {
                binding.txtChangePriceItemRecycler.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.colorLoss
                    )
                )

                binding.txtChangePriceItemRecycler.text = "$showPrice%"


            } else if (changePrice > 0) {

                binding.txtChangePriceItemRecycler.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.colorGain
                    )
                )

                binding.txtChangePriceItemRecycler.text = "+$showPrice% "

            } else {

                binding.txtChangePriceItemRecycler.text = "0"
            }


            Glide.with(binding.root.context)
                .load(BASE_URL_IMAGE + dataCoin.coinInfo.imageUrl)
                .transform(RoundedCornersTransformation(4, 4))
                .into(binding.imgCoinItemRecycler)

            itemView.setOnClickListener {

                coinsEvent.onCoinClicked(data[adapterPosition])

            }


        }




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewCoinHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemRecyclerBinding.inflate(inflater, parent, false)
        return ViewCoinHolder(binding.root)

    }


    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: ViewCoinHolder, position: Int) {

        holder.bindData(data[position])
    }






}