package com.rama.jaguar.activities.settings

import android.content.Intent
import com.rama.jaguar.R
import com.rama.jaguar.activities.AboutActivity
import com.rama.jaguar.activities.SettingsActivity
import com.rama.bohio.util.UiActions

class SettingsBasicController(private val activity: SettingsActivity) {

    private val prefs get() = activity.prefs

    fun setup() {
        UiActions.setupButton(activity, R.id.about_button) {
            activity.startActivity(Intent(activity, AboutActivity::class.java))
        }

        UiActions.setupButton(activity, R.id.close_button) {
            activity.finish()
        }
    }
}