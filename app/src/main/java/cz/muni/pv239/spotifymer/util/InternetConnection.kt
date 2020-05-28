package cz.muni.pv239.spotifymer.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


object InternetConnection {

    fun isNetworkReacheable(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        var result = false
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            cm.getNetworkCapabilities(cm.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                result = true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                result = true
            }
        }
        return result
    }
}