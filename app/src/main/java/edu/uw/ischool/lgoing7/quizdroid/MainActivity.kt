package edu.uw.ischool.lgoing7.quizdroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.ArrayAdapter
import android.content.Intent

data class Topic(
    val title: String,
    val shortDesc: String,
    val longDesc: String,
    val questions: List<Question>
)

data class Question(
    val text: String,
    val answers: List<String>,
    val correctIndex: Int
)

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.topicList)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            topics.map { it.title }
        )

        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, TopicOverviewActivity::class.java)
            intent.putExtra("position", position)
            startActivity(intent)
        }
    }

    companion object {
        val topics = listOf(
            Topic(
                "Math",
                "Test your basic math skills",
                "This quiz will test your knowledge of elementary-level math",
                listOf(
                    Question(
                        "What is 2 + 27?",
                        listOf("31", "29", "30", "28"),
                        1
                    ),
                    Question(
                        "What is 8 x 8?",
                        listOf("56", "56", "60", "64"),
                        3
                    ),
                    Question(
                        "What is 20 - 12?",
                        listOf("11", "9", "8", "14"),
                        2
                    )
                )
            ),
            Topic(
                "Physics",
                "Basic physics concepts",
                "Test your knowledge of basic physics",
                listOf(
                    Question(
                        "Who developed the mass-energy equivalence E=mc²?",
                        listOf(
                            "Benjamin Franklin",
                            "Albert Einstein",
                            "Henry Ford",
                            "The Wright Brothers"
                        ),
                        1
                    ),
                    Question(
                        "What is the speed of light (approximate)?",
                        listOf(
                            "~200,000 km/s",
                            "~250,000 km/s",
                            "~300,000 km/s",
                            "~350,000 km/s"
                        ),
                        2
                    ),
                    Question(
                        "What is the speed of sound (approximate)?",
                        listOf(
                            "~0.35 km/s",
                            "~5 km/s",
                            "~100 km/s",
                            "~200,000 km/s"
                        ),
                        0
                    )
                )
            ),
            Topic(
                "Marvel Super Heroes",
                "Test your Marvel knowledge",
                "Quiz yourself on the MCU and Marvel Super Heroes!",
                listOf(
                    Question(
                        "What is the Hulk's real name",
                        listOf(
                            "Tony Stark",
                            "Steve Rogers",
                            "Bruce Banner",
                            "Peter Parker"
                        ),
                        2
                    ),
                    Question(
                        "What animal is one of the members of Guardians of the Galaxy",
                        listOf(
                            "Skunk",
                            "Raccoon",
                            "Rabbit",
                            "Meerkat"
                        ),
                        1
                    ),
                    Question(
                        "What type of doctor is Doctor Strange?",
                        listOf(
                            "Optometrist",
                            "Psychiatrist",
                            "Neurosurgeon",
                            "Cardiologist"
                        ),
                        2
                    )
                )
            )
        )
    }
}