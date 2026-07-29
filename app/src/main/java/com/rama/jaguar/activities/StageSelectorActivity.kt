package com.rama.jaguar.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.rama.jaguar.CsActivity
import com.rama.jaguar.R

class StageSelectorActivity : CsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_stage_selector)

        val root = findViewById<View>(R.id.root)
        applyEdgeToEdgePadding(root)
        applyCurrentTheme(root)

        findViewById<View>(R.id.lvl1_en).setOnClickListener { startStage(1) }
        findViewById<View>(R.id.lvl2_en).setOnClickListener { startStage(2) }
    }

    private fun startStage(grade: Int) {
        val intent = Intent(this, StageActivity::class.java)
        intent.putExtra(StageActivity.EXTRA_GRADE, grade)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        applyKeepScreenOnPref(prefs)
    }
}
