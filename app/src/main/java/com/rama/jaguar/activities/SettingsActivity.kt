package com.rama.jaguar.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.rama.jaguar.CsActivity
import com.rama.jaguar.R
import com.rama.jaguar.activities.settings.SettingsAppearanceController
import com.rama.jaguar.activities.settings.SettingsBasicController
import com.rama.jaguar.activities.settings.SettingsCheckboxController
import com.rama.jaguar.activities.settings.SettingsLanguageController

class SettingsActivity : CsActivity() {
    private lateinit var appearanceController: SettingsAppearanceController
    private lateinit var settingsRootView: View
    val FONT_PICK_REQUEST = 1002

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_settings)

        settingsRootView = findViewById(R.id.settings_root)
        applyEdgeToEdgePadding(settingsRootView)
        applyCurrentTheme(settingsRootView)

        SettingsBasicController(this).setup()
        appearanceController = SettingsAppearanceController(this).also { it.setup() }
        SettingsLanguageController(this).setup()
        SettingsCheckboxController(this).setup()
    }

    override fun onResume() {
        super.onResume()
        applyKeepScreenOnPref(prefs)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        appearanceController.onActivityResult(requestCode, resultCode, data)
    }
}