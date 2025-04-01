package ir.valaroid.digitalcoins.firstActivity

import ir.valaroid.digitalcoins.model.DataCoins

interface CoinsEvent {

    fun onCoinClicked(dataCoin: DataCoins.Data)

}