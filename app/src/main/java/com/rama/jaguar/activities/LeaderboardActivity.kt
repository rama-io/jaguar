package com.rama.jaguar.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.rama.jaguar.CsActivity
import com.rama.jaguar.ListItem
import com.rama.jaguar.R

class LeaderboardActivity : CsActivity() {

    private lateinit var listContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_leaderboard)

        val root = findViewById<View>(R.id.root)
        applyEdgeToEdgePadding(root)
        applyCurrentTheme(root)

        listContainer = findViewById(R.id.leaderboard_list)

        val openSettingsBtn = findViewById<FrameLayout>(R.id.open_settings)
        openSettingsBtn.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        findViewById<View>(R.id.lvl1).setOnClickListener { startStage(1) }
        findViewById<View>(R.id.lvl2).setOnClickListener { startStage(2) }
    }

    private fun startStage(grade: Int) {
        val intent = Intent(this, StageActivity::class.java)
        intent.putExtra(StageActivity.EXTRA_GRADE, grade)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        applyKeepScreenOnPref(prefs)
        refreshLeaderboard()
    }

    private fun refreshLeaderboard() {
        listContainer.removeAllViews()
        val entries = prefs.getLeaderboard()
        if (entries.isEmpty()) return
        entries.forEachIndexed { index, entry ->
            val row = ListItem(this)
            row.bind(index + 1, entry)
            listContainer.addView(row)
        }
        applyCurrentTheme(listContainer)
    }
}
