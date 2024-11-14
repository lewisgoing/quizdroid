package edu.uw.ischool.lgoing7.quizdroid

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class TopicAdapter(context: Context, private val topics: List<Topic>) :
    ArrayAdapter<Topic>(context, android.R.layout.simple_list_item_2, topics) {

    override fun getCount(): Int = topics.size

    override fun getItem(position: Int): Topic? = topics.getOrNull(position)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)

        val topic = topics[position]

        Log.d("TopicAdapter", "Creating view for topic: ${topic.title} at position $position")

        view.findViewById<TextView>(android.R.id.text1).apply {
            text = topic.title
            isClickable = true
        }
        view.findViewById<TextView>(android.R.id.text2).text = topic.shortDesc

        return view
    }
}