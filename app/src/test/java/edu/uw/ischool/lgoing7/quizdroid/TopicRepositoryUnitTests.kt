package edu.uw.ischool.lgoing7.quizdroid

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class TopicRepositoryUnitTests {
    private lateinit var repository: TopicRepository

    @Before
    fun setup() {
        repository = InMemoryTopicRepository()
    }

    @Test
    fun testGetAllTopics() {
        val topics = repository.getAllTopics()
        // testing that we have all 3 topics (Math, Physics, Marvel) and that titles match
        assertEquals(3, topics.size)

        val titles = topics.map { it.title }.toSet()
        assertTrue(titles.contains("Math"))
        assertTrue(titles.contains("Physics"))
        assertTrue(titles.contains("Marvel Super Heroes"))
    }

    @Test
    fun testGetTopicByValidIndex() {
        // Testing getting Math by index 0
        val mathTopic = repository.getTopicByIndex(0)
        assertNotNull(mathTopic)
        assertEquals("Math", mathTopic?.title)
        assertEquals(3, mathTopic?.questions?.size)  // Should have 3 questions
    }

    @Test
    fun testGetTopicByInvalidIndex() {
        // Testing getting a topic with an out of range index (99)
        val invalidTopic = repository.getTopicByIndex(99)
        assertNull(invalidTopic)
    }
}