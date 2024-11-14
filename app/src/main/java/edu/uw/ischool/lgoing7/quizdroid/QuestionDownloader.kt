package edu.uw.ischool.lgoing7.quizdroid

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch

class QuestionDownloader(
    private val context: Context,
    private val lifecycleScope: LifecycleCoroutineScope? = null
) {
    private val tag = "QuestionDownloader"
    private val networkUtils = NetworkUtils(context)
    private val preferencesManager = PreferencesManager(context)

    suspend fun downloadQuestions(): Boolean {
        if (!preferencesManager.shouldDownload()) {
            Log.d(tag, "Skipping download - within refresh interval")
            return false
        }

        if (!networkUtils.isNetworkAvailable()) {
            Log.d(tag, "Network is not available")
            withContext(Dispatchers.Main) {
                networkUtils.showNoInternetDialog()
            }
            return false
        }

        return try {
            val url = preferencesManager.dataUrl
            Log.d(tag, "Attempting to download from URL: $url")

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Downloading from: $url", Toast.LENGTH_LONG).show()
            }

            val tempFile = File(context.cacheDir, "questions_temp.json")
            val targetFile = File(context.filesDir, "questions.json")

            val success = withContext(Dispatchers.IO) {
                val connection = URL(url).openConnection() as HttpURLConnection
                try {
                    connection.apply {
                        requestMethod = "GET"
                        connectTimeout = 15000
                        readTimeout = 15000
                        doInput = true
                        setRequestProperty("User-Agent", "Mozilla/5.0")
                        setRequestProperty("Accept", "*/*")
                    }

                    Log.d(tag, "Connecting to server")
                    val responseCode = connection.responseCode
                    Log.d(tag, "Server response code: $responseCode")

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Log.d(tag, "Connection success, reading response...")

                        val response = connection.inputStream.bufferedReader().use { reader ->
                            reader.readText()
                        }

                        Log.d(tag, "Writing to temp file")
                        FileOutputStream(tempFile).use { output ->
                            output.write(response.toByteArray())
                        }

                        if (tempFile.exists() && tempFile.length() > 0) {
                            Log.d(tag, "Temp file created successfully")
                            tempFile.copyTo(targetFile, overwrite = true)
                            tempFile.delete()
                            true
                        } else {
                            Log.e(tag, "Temp file validation failed")
                            false
                        }
                    } else {
                        Log.e(tag, "Server returned error code: $responseCode")
                        false
                    }
                } finally {
                    connection.disconnect()
                }
            }

            if (success) {
                preferencesManager.updateLastDownloadTime()
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Download completed successfully",
                        Toast.LENGTH_SHORT).show()
                }
            }

            success
        } catch (e: Exception) {
            Log.e(tag, "Download failed", e)
            withContext(Dispatchers.Main) {
                networkUtils.showDownloadFailedDialog {
                    lifecycleScope?.launch {
                        downloadQuestions()
                    }
                }
            }
            false
        }
    }
}