package com.cavalinho.cavalinhoapp.service.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.gson.Gson

open class BaseRepository(context: Context) {

    val mContext: Context = context

//    fun fail(code: Int) = code != UserConstants

    fun failRespose(respose: String): String {
        return Gson().fromJson(respose, String::class.java)
    }

    /**
     * Verifica se existe conexão com internet
     */
    fun isConnectionAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = cm.activeNetwork ?: return false
        val actNw = cm.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
        return result
    }

}