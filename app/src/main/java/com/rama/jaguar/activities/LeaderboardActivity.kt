package com.rama.jaguar.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.rama.jaguar.CsActivity
import com.rama.jaguar.R

class LeaderboardActivity : CsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_leaderboard)

        val root = findViewById<View>(R.id.root)
        applyEdgeToEdgePadding(root)
        applyCurrentTheme(root)

        val openSettingsBtn = findViewById<FrameLayout>(R.id.open_settings)
        openSettingsBtn.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        }

        val lvl1 = findViewById<View>(R.id.lvl1)
        lvl1.setOnClickListener {
            startActivity(Intent(this, StageActivity::class.java))
            true
        }
    }

    override fun onResume() {
        super.onResume()
        if (prefs.getBoolean(
                com.rama.bohio.objects.PrefKeys.SYSTEM_PREVENT_SLEEP,
                false
            )
        ) {
            window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
}