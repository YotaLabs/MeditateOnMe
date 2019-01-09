package com.yotalabs.meditateonme.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * @author SashaKhyzhun
 * Created on 1/9/19.
 */
fun isOnline(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo
    return netInfo != null && netInfo.isConnectedOrConnecting
}
