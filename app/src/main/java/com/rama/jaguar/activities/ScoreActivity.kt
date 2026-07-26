package com.rama.jaguar.activities

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.rama.bohio.objects.PrefKeys
import com.rama.jaguar.CsActivity
import com.rama.jaguar.R

class ScoreActivity : CsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_score_submission)

        val root = findViewById<View>(R.id.root)
        applyEdgeToEdgePadding(root)
        applyCurrentTheme(root)
    }

    override fun onResume() {
        super.onResume()
        if (prefs.getBoolean(
                PrefKeys.SYSTEM_PREVENT_SLEEP,
                false
            )
        ) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
}