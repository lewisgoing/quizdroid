package edu.uw.ischool.lgoing7.quizdroid

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "quiz_preferences",
        Context.MODE_PRIVATE
    )

    companion object {
        const val KEY_DATA_URL = "data_url"
        const val KEY_REFRESH_INTERVAL = "refresh_interval"

        const val DEFAULT_URL = "http://tednewardsandbox.site44.com/questions.json"
        const val DEFAULT_REFRESH_INTERVAL = 15 // minutes
    }

    var dataUrl: String
        get() = prefs.getString(KEY_DATA_URL, DEFAULT_URL) ?: DEFAULT_URL
        set(value) = prefs.edit().putString(KEY_DATA_URL, value).apply()

    var refreshInterval: Int
        get() = prefs.getInt(KEY_REFRESH_INTERVAL, DEFAULT_REFRESH_INTERVAL)
        set(value) = prefs.edit().putInt(KEY_REFRESH_INTERVAL, value).apply()
}