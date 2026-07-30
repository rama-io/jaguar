package com.rama.jaguar

import android.content.Context
import android.view.WindowManager
import com.rama.bohio.activity.BohioActivity
import com.rama.bohio.objects.PrefKeys
import com.rama.jaguar.managers.PrefsManager

abstract class CsActivity : BohioActivity() {
    val prefs by lazy { PrefsManager.getInstance(this) }

    override fun attachBaseContext(newBase: Context) {
        PrefsManager.getInstance(newBase.applicationContext).also {
            it.initPrefs()
        }
        super.attachBaseContext(newBase)
    }

    fun BohioActivity.applyKeepScreenOnPref(prefs: PrefsManager) {
        if (prefs.getBoolean(PrefKeys.SYSTEM_PREVENT_SLEEP, false)) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
}
