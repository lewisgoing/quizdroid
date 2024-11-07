package edu.uw.ischool.lgoing7.quizdroid

// Domain models
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

// Repository interface
interface TopicRepository {
    fun getAllTopics(): List<Topic>
    fun getTopicByIndex(index: Int): Topic?
    fun getTopicCount(): Int
}

// In-memory implementation
class InMemoryTopicRepository : TopicRepository {
    private val topics = listOf(
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

    override fun getAllTopics(): List<Topic> = topics
    override fun getTopicByIndex(index: Int): Topic? = topics.getOrNull(index)
    override fun getTopicCount(): Int = topics.size
}