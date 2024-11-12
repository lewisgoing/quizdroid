package edu.uw.ischool.lgoing7.quizdroid

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var repository: TopicRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        repository = QuizApp.getInstance().getRepository()
        listView = findViewById(R.id.topicList)

        lifecycleScope.launch {
            val topics = repository.loadTopics()
            val adapter = TopicAdapter(this@MainActivity, topics)
            listView.adapter = adapter
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, TopicOverviewActivity::class.java)
            intent.putExtra("position", position)
            startActivity(intent)
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