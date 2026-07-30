package com.rama.jaguar.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.rama.jaguar.CsActivity
import com.rama.jaguar.R
import com.rama.jaguar.braille.BrailleLanguage

class StageSelectorActivity : CsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_stage_selector)

        val root = findViewById<View>(R.id.root)
        applyEdgeToEdgePadding(root)
        applyCurrentTheme(root)

        findViewById<View>(R.id.lvl1_eng).setOnClickListener {
            startStage(
                BrailleLanguage.ENGLISH,
                1
            )
        }
        findViewById<View>(R.id.lvl2_eng).setOnClickListener {
            startStage(
                BrailleLanguage.ENGLISH,
                2
            )
        }
        findViewById<View>(R.id.lvl1_esp).setOnClickListener {
            startStage(
                BrailleLanguage.SPANISH,
                1
            )
        }
        findViewById<View>(R.id.lvl1_por).setOnClickListener {
            startStage(
                BrailleLanguage.PORTUGUESE,
                1
            )
        }
    }

    private fun startStage(language: BrailleLanguage, grade: Int) {
        val intent = Intent(this, StageActivity::class.java)
        intent.putExtra(StageActivity.EXTRA_LANGUAGE, language.code)
        intent.putExtra(StageActivity.EXTRA_GRADE, grade)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        applyKeepScreenOnPref(prefs)
    }
}
