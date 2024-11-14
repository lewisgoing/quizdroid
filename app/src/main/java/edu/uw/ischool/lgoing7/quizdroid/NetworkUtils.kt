package edu.uw.ischool.lgoing7.quizdroid

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import androidx.appcompat.app.AlertDialog

class NetworkUtils(private val context: Context) {
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    private fun isAirplaneModeOn(): Boolean {
        return Settings.Global.getInt(context.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    }

    fun showNoInternetDialog() {
        if (isAirplaneModeOn()) {
            AlertDialog.Builder(context)
                .setTitle("No Internet Connection")
                .setMessage("Airplane mode is on. Would you like to turn it off?")
                .setPositiveButton("Open Settings") { _, _ ->
                    context.startActivity(Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS))
                }
                .setNegativeButton("Cancel", null)
                .show()
        } else {
            AlertDialog.Builder(context)
                .setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again.")
                .setPositiveButton("OK", null)
                .show()
        }
    }

    fun showDownloadFailedDialog(retry: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle("Download Failed")
            .setMessage("Failed to download questions. Would you like to quit or retry?")
            .setPositiveButton("Retry") { _, _ -> retry() }
            .setNegativeButton("Quit") { _, _ ->
                if (context is MainActivity) {
                    context.finish()
                }
            }
            .show()
    }
}