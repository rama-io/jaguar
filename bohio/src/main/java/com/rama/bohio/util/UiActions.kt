package com.rama.bohio.util

import android.app.Activity
import android.content.Intent
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.Toast

object UiActions {

    fun setupButton(activity: Activity, id: Int, action: () -> Unit) {
        val view = activity.findViewById<View>(id)
        setClickWithHaptics(view, action)
    }

    fun setClickWithHaptics(view: View, action: () -> Unit) {
        view.setOnClickListener {
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
            action()
        }
    }

    fun openIntent(activity: Activity, intent: Intent, error: String) {
        if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(intent)
        } else {
            Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
        }
    }
}