package edu.uw.ischool.lgoing7.quizdroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.ArrayAdapter
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var repository: TopicRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = QuizApp.getInstance().getRepository()
        listView = findViewById(R.id.topicList)

        // Fixed adapter implementation
        val adapter = object : ArrayAdapter<Topic>(
            this,
            android.R.layout.simple_list_item_2,
            repository.getAllTopics().toMutableList()
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: layoutInflater.inflate(android.R.layout.simple_list_item_2, parent, false)
                val text1 = view.findViewById<TextView>(android.R.id.text1)
                val text2 = view.findViewById<TextView>(android.R.id.text2)

                val topic = getItem(position)
                text1.text = topic?.title
                text2.text = topic?.shortDesc

                return view
            }
        }

        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, TopicOverviewActivity::class.java)
            intent.putExtra("position", position)
            startActivity(intent)
        }
    }
}