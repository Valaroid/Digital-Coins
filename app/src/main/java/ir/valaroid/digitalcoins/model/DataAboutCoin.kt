package ir.valaroid.digitalcoins.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataAboutCoin(
     val websSite : String?="No555-Data",
     val twitter : String?="No55-Data",
     val reddit : String?="No555-Data",
     val github : String?="No-555Data",
     val dec : String?="No-5555Data"
) : Parcelable
