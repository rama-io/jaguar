package com.rama.jaguar

import org.json.JSONArray
import org.json.JSONObject

data class LeaderboardEntry(
    val name: String,
    val grade: Int,
    val score: Int,
    val total: Int,
    val timeMillis: Long,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun toJson(): JSONObject = JSONObject().apply {
        put("name", name)
        put("grade", grade)
        put("score", score)
        put("total", total)
        put("timeMillis", timeMillis)
        put("timestamp", timestamp)
    }

    companion object {
        fun fromJson(json: JSONObject): LeaderboardEntry = LeaderboardEntry(
            name = json.optString("name", "?"),
            grade = json.optInt("grade", 1),
            score = json.optInt("score", 0),
            total = json.optInt("total", 0),
            timeMillis = json.optLong("timeMillis", 0L),
            timestamp = json.optLong("timestamp", 0L)
        )

        fun listToJson(entries: List<LeaderboardEntry>): String {
            val array = JSONArray()
            entries.forEach { array.put(it.toJson()) }
            return array.toString()
        }

        fun listFromJson(raw: String): List<LeaderboardEntry> {
            if (raw.isBlank()) return emptyList()
            return runCatching {
                val array = JSONArray(raw)
                (0 until array.length()).map { fromJson(array.getJSONObject(it)) }
            }.getOrDefault(emptyList())
        }
    }
}
