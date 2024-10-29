package edu.uw.ischool.lgoing7.quizdroid

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.content.Intent

class TopicOverviewActivity : AppCompatActivity() {
    private var position = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

        position = intent.getIntExtra("position", 0)
        val topic = MainActivity.topics[position]

        findViewById<TextView>(R.id.descriptionText).text = topic.longDesc
        findViewById<TextView>(R.id.questionCountText).text =
            "This quiz contains ${topic.questions.size} questions"

        findViewById<Button>(R.id.beginButton).setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("questionIndex", 0)
            intent.putExtra("correctCount", 0)
            startActivity(intent)
        }
    }
}