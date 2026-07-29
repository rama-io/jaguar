package com.rama.jaguar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView

class ListItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.list_item, this, true)
        attrs?.let { setAttrs(context, it) }
    }

    private fun setAttrs(context: Context, attrs: AttributeSet) {
        for (i in 0 until attrs.attributeCount) {
            when (attrs.getAttributeName(i)) {
                "value" -> attrs.getAttributeValue(i)?.let { setValue(it) }
            }
        }
    }

    fun setValue(text: String) {
        findViewById<TextView>(R.id.name)?.text = text
    }

    fun bind(rank: Int, entry: LeaderboardEntry) {
        findViewById<TextView>(R.id.counter).text = "$rank."
        findViewById<TextView>(R.id.name).text = entry.name
        findViewById<TextView>(R.id.lvl).text = "${entry.language}${entry.grade}"
        findViewById<TextView>(R.id.matches).text = "${entry.score}/${entry.total}"
        findViewById<TextView>(R.id.time).text = formatTime(entry.timeMillis)
    }

    private fun formatTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%02d", minutes, seconds)
    }
}
