package edu.uw.ischool.lgoing7.quizdroid

import android.content.Context
import org.json.JSONArray
import java.io.File


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

interface TopicRepository {
    suspend fun loadTopics(): List<Topic>
    fun getTopicByIndex(index: Int): Topic?
    fun getTopicCount(): Int
}

class InMemoryTopicRepository(
    private val context: Context
) : TopicRepository {
    private lateinit var topics: List<Topic>

    override suspend fun loadTopics(): List<Topic> {
        if (!::topics.isInitialized) {
            val json = when {

                File(context.filesDir, "questions.json").exists() -> {
                    File(context.filesDir, "questions.json").readText()
                }
                else -> {
                    context.assets.open("questions.json").bufferedReader().use { it.readText() }
                }
            }
            val jsonArray = JSONArray(json)
            topics = parseTopics(jsonArray)
        }
        return topics
    }

    private fun parseTopics(jsonArray: JSONArray): List<Topic> {
        val topics = mutableListOf<Topic>()
        for (i in 0 until jsonArray.length()) {
            val jsonTopic = jsonArray.getJSONObject(i)
            val title = jsonTopic.getString("title")
            val desc = jsonTopic.getString("desc")
            val jsonQuestions = jsonTopic.getJSONArray("questions")
            val questions = parseQuestions(jsonQuestions)
            topics.add(Topic(title, desc, desc, questions))
        }
        return topics
    }

    private fun parseQuestions(jsonArray: JSONArray): List<Question> {
        val questions = mutableListOf<Question>()
        for (i in 0 until jsonArray.length()) {
            val jsonQuestion = jsonArray.getJSONObject(i)
            val text = jsonQuestion.getString("text")
            val correctIndex = jsonQuestion.getInt("answer") - 1
            val answers = jsonQuestion.getJSONArray("answers").let {
                List(it.length()) { index -> it.getString(index) }
            }
            questions.add(Question(text, answers, correctIndex))
        }
        return questions
    }

    override fun getTopicByIndex(index: Int): Topic? = topics.getOrNull(index)
    override fun getTopicCount(): Int = topics.size
}