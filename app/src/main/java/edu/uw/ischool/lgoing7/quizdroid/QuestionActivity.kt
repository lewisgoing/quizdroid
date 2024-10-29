package edu.uw.ischool.lgoing7.quizdroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.RadioGroup
import android.widget.RadioButton
import android.content.Intent

class QuestionActivity : AppCompatActivity() {
    private lateinit var questionText: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var submitButton: Button
    private var topicPosition = 0
    private var questionIndex = 0
    private var correctCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        topicPosition = intent.getIntExtra("position", 0)
        questionIndex = intent.getIntExtra("questionIndex", 0)
        correctCount = intent.getIntExtra("correctCount", 0)

        questionText = findViewById(R.id.questionText)
        radioGroup = findViewById(R.id.answerGroup)
        submitButton = findViewById(R.id.submitButton)

        val topic = MainActivity.topics[topicPosition]
        val question = topic.questions[questionIndex]

        questionText.text = question.text

        // Creates radio buttons for all answers
        question.answers.forEachIndexed { index, answer ->
            val radioButton = RadioButton(this)
            radioButton.text = answer
            radioButton.id = index
            radioGroup.addView(radioButton)
        }

        submitButton.isEnabled = false

        radioGroup.setOnCheckedChangeListener { _, _ ->
            submitButton.isEnabled = true
        }

        submitButton.setOnClickListener {
            val selectedAnswerIndex = radioGroup.checkedRadioButtonId
            if (selectedAnswerIndex != -1) {
                val intent = Intent(this, AnswerActivity::class.java)
                intent.putExtra("position", topicPosition)
                intent.putExtra("questionIndex", questionIndex)
                intent.putExtra("selectedAnswer", selectedAnswerIndex)
                intent.putExtra("correctCount", correctCount)
                startActivity(intent)
            }
        }
    }
}