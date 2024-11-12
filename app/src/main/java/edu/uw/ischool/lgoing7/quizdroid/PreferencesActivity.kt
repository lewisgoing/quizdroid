package edu.uw.ischool.lgoing7.quizdroid

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PreferencesActivity : AppCompatActivity() {
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var urlEditText: EditText
    private lateinit var intervalEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        try {
            preferencesManager = PreferencesManager(applicationContext)

            urlEditText = findViewById(R.id.urlEditText)
            intervalEditText = findViewById(R.id.intervalEditText)
            val saveButton = findViewById<Button>(R.id.saveButton)

            urlEditText.setText(preferencesManager.dataUrl)
            intervalEditText.setText(preferencesManager.refreshInterval.toString())

            saveButton.setOnClickListener {
                try {
                    savePreferences()
                    Toast.makeText(this, "Preferences saved", Toast.LENGTH_SHORT).show()
                    finish()
                } catch (e: Exception) {
                    Log.e("PreferencesActivity", "Error saving preferences", e)
                    Toast.makeText(this, "Error saving preferences", Toast.LENGTH_SHORT).show()
                }
            }

        } catch (e: Exception) {
            Log.e("PreferencesActivity", "Error initializing preferences", e)
            Toast.makeText(this, "Error initializing preferences", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun savePreferences() {
        val url = urlEditText.text.toString()
        val intervalStr = intervalEditText.text.toString()

        if (url.isNotBlank()) {
            preferencesManager.dataUrl = url
        }

        val interval = intervalStr.toIntOrNull()
        if (interval != null && interval > 0) {
            preferencesManager.refreshInterval = interval
        } else {
            Toast.makeText(this, "Please enter a valid interval", Toast.LENGTH_SHORT).show()
            return
        }
    }
}