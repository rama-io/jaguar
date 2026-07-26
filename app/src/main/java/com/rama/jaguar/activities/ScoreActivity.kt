package com.rama.jaguar.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.rama.bohio.objects.PrefKeys
import com.rama.jaguar.CsActivity
import com.rama.jaguar.LeaderboardEntry
import com.rama.jaguar.R

class ScoreActivity : CsActivity() {

    companion object {
        const val EXTRA_GRADE = "extra_grade"
        const val EXTRA_SCORE = "extra_score"
        const val EXTRA_TOTAL = "extra_total"
        const val EXTRA_TIME_MILLIS = "extra_time_millis"
    }

    private var grade = 1
    private var score = 0
    private var total = 0
    private var timeMillis = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_score_submission)

        val root = findViewById<View>(R.id.root)
        applyEdgeToEdgePadding(root)
        applyCurrentTheme(root)

        grade = intent.getIntExtra(EXTRA_GRADE, 1)
        score = intent.getIntExtra(EXTRA_SCORE, 0)
        total = intent.getIntExtra(EXTRA_TOTAL, 0)
        timeMillis = intent.getLongExtra(EXTRA_TIME_MILLIS, 0L)

        findViewById<TextView>(R.id.score).text = "$score/$total"
        findViewById<TextView>(R.id.time).text = formatTime(timeMillis)

        val nameInput = findViewById<EditText>(R.id.name)
        nameInput.setText("")

        findViewById<Button>(R.id.submit).setOnClickListener {
            val name = nameInput.text?.toString()?.trim().orEmpty()
            if (name.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_name_empty), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            prefs.addLeaderboardEntry(
                LeaderboardEntry(
                    name = name,
                    grade = grade,
                    score = score,
                    total = total,
                    timeMillis = timeMillis
                )
            )

            val intent = Intent(this, LeaderboardActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (prefs.getBoolean(PrefKeys.SYSTEM_PREVENT_SLEEP, false)) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    private fun formatTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%02d", minutes, seconds)
    }
}
