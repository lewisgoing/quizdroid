package edu.uw.ischool.lgoing7.quizdroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var repository: TopicRepository
    private lateinit var downloader: QuestionDownloader
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        repository = QuizApp.getInstance().getRepository()
        preferencesManager = PreferencesManager(this)
        downloader = QuestionDownloader(this, lifecycleScope)
        listView = findViewById(R.id.topicList)

        loadTopics()

        if (preferencesManager.shouldDownload()) {
            initiateDownload()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, TopicOverviewActivity::class.java)
            intent.putExtra("position", position)
            startActivity(intent)
        }
    }

    private fun loadTopics() {
        lifecycleScope.launch {
            try {
                val topics = repository.loadTopics()
                Log.d("MainActivity", "loaded ${topics.size} topics")
                val adapter = TopicAdapter(this@MainActivity, topics)
                listView.adapter = adapter
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Log.e("MainActivity", "error loading topics", e)
                Toast.makeText(
                    this@MainActivity,
                    "error loading topics",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initiateDownload() {
        lifecycleScope.launch {
            try {
                val success = withContext(Dispatchers.IO) {
                    downloader.downloadQuestions()
                }
                if (success) {
                    repository = QuizApp.getInstance().getRepository()
                    // reload
                    loadTopics()
                    Toast.makeText(
                        this@MainActivity,
                        "Topics updated",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error downloading JSON", e)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_preferences -> {
                val intent = Intent(this, PreferencesActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}