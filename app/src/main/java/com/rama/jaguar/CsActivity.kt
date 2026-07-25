package com.rama.jaguar

import android.content.Context
import com.rama.bohio.activity.BohioActivity
import com.rama.jaguar.managers.PrefsManager

abstract class CsActivity : BohioActivity() {
    val prefs by lazy { PrefsManager.getInstance(this) }

    override fun attachBaseContext(newBase: Context) {
        PrefsManager.getInstance(newBase.applicationContext).also {
            it.initPrefs()
        }
        super.attachBaseContext(newBase)
    }
}
