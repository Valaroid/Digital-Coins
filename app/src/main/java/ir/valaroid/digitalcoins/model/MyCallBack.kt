package ir.valaroid.digitalcoins.model

interface MyCallBack<T>{
    fun onSuccess(data:T)
    fun onFailure(message:String)

}