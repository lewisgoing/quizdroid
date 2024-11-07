package edu.uw.ischool.lgoing7.quizdroid

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.content.Intent

class TopicOverviewActivity : AppCompatActivity() {
    private var position = 0
    private lateinit var repository: TopicRepository

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

        repository = QuizApp.getInstance().getRepository()
        position = intent.getIntExtra("position", 0)

        val topic = repository.getTopicByIndex(position)
        topic?.let {
            findViewById<TextView>(R.id.descriptionText).text = it.longDesc
            findViewById<TextView>(R.id.questionCountText).text =
                "This quiz contains ${it.questions.size} questions"
        }

        findViewById<Button>(R.id.beginButton).setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("questionIndex", 0)
            intent.putExtra("correctCount", 0)
            startActivity(intent)
        }
    }
}