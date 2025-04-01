package ir.valaroid.digitalcoins.secondActivity

import com.robinhood.spark.SparkAdapter
import ir.valaroid.digitalcoins.model.DataChart


class ChartAdapter(private val data : List<DataChart.Data> ,
    private val baseLine : String?) : SparkAdapter() {




    override fun getCount(): Int {
       return data.size
    }




    override fun getItem(index: Int): DataChart.Data {

        return data[index]
    }




    override fun getY(index: Int): Float {

        return data[index].close.toFloat()
    }


    override fun hasBaseLine(): Boolean {
        return true

    }

    override fun getBaseLine(): Float {
        return baseLine?.toFloat() ?:super.getBaseLine()
    }



}