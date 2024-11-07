package edu.uw.ischool.lgoing7.quizdroid

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.content.Intent

class AnswerActivity : AppCompatActivity() {
    private lateinit var repository: TopicRepository

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        repository = QuizApp.getInstance().getRepository()
        val position = intent.getIntExtra("position", 0)
        val questionIndex = intent.getIntExtra("questionIndex", 0)
        val selectedAnswer = intent.getIntExtra("selectedAnswer", -1)
        var correctCount = intent.getIntExtra("correctCount", 0)

        val topic = repository.getTopicByIndex(position)
        topic?.let {
            val question = it.questions[questionIndex]

            findViewById<TextView>(R.id.yourAnswerText).text =
                "Your answer: ${question.answers[selectedAnswer]}"
            findViewById<TextView>(R.id.correctAnswerText).text =
                "Correct answer: ${question.answers[question.correctIndex]}"

            if (selectedAnswer == question.correctIndex) {
                correctCount++
            }

            findViewById<TextView>(R.id.scoreText).text =
                "You have $correctCount out of ${questionIndex + 1} correct"

            val nextButton = findViewById<Button>(R.id.nextButton)

            if (questionIndex + 1 < topic.questions.size) {
                nextButton.text = "Next"
                nextButton.setOnClickListener {
                    val intent = Intent(this, QuestionActivity::class.java)
                    intent.putExtra("position", position)
                    intent.putExtra("questionIndex", questionIndex + 1)
                    intent.putExtra("correctCount", correctCount)
                    startActivity(intent)
                    finish()
                }
            } else {
                nextButton.text = "Finish"
                nextButton.setOnClickListener {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
            }
        }
    }
}