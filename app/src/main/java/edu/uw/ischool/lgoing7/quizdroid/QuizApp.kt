package edu.uw.ischool.lgoing7.quizdroid

import android.app.Application

class QuizApp : Application() {
    private lateinit var repository: TopicRepository
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate() {
        super.onCreate()
        instance = this
        preferencesManager = PreferencesManager(applicationContext)
        repository = InMemoryTopicRepository(this)
    }

    fun getRepository(): TopicRepository = repository
    fun getPreferencesManager(): PreferencesManager = preferencesManager

    companion object {
        private lateinit var instance: QuizApp
        fun getInstance(): QuizApp = instance
    }
}