package edu.uw.ischool.lgoing7.quizdroid

import android.app.Application
import android.util.Log

class QuizApp : Application() {
    private lateinit var repository: TopicRepository

    override fun onCreate() {
        super.onCreate()
        Log.d("QuizApp", "QuizApp is being initialized")
        repository = InMemoryTopicRepository()
    }

    fun getRepository(): TopicRepository {
        return repository
    }

    companion object {
        private lateinit var instance: QuizApp

        fun getInstance(): QuizApp {
            return instance
        }
    }

    init {
        instance = this
    }
}