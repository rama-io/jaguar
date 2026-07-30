package com.rama.bohio.activity

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.ComponentActivity
import com.rama.bohio.managers.FontManager
import com.rama.bohio.managers.ThemeManager
import com.rama.bohio.objects.PrefKeys
import com.rama.bohio.util.Dimens.dpToPx
import com.rama.bohio.util.LocaleHelper

abstract class BohioActivity : ComponentActivity() {

    private var lastKnownLanguage: String? = null
    private var lastKnownTheme: String? = null
    private var lastKnownUiScale: Float = -1f

    // Context wrapping (locale + UI scale)

    override fun attachBaseContext(newBase: Context) {
        val localeContext = LocaleHelper.wrapContext(newBase)

        val scale = rawPrefs(localeContext).getFloat(PrefKeys.APP_UI_SCALE, 1f)

        val context = if (scale != 1f) {
            val config = Configuration(localeContext.resources.configuration)
            config.densityDpi =
                (localeContext.resources.displayMetrics.densityDpi * scale).toInt()
            localeContext.createConfigurationContext(config)
        } else {
            localeContext
        }

        super.attachBaseContext(context)
    }

    // Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val p = rawPrefs(this)
        lastKnownLanguage = p.getString(PrefKeys.APP_LANGUAGE, "")
        lastKnownTheme = p.getString(PrefKeys.APP_THEME_NAME, "")
        lastKnownUiScale = p.getFloat(PrefKeys.APP_UI_SCALE, 1f)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
    }

    override fun onResume() {
        super.onResume()

        val p = rawPrefs(this)

        val lang = p.getString(PrefKeys.APP_LANGUAGE, "") ?: ""
        if (lang != lastKnownLanguage) {
            lastKnownLanguage = lang
            if (shouldRecreateOnSettingsChange()) {
                recreate(); return
            }
        }

        val theme = p.getString(PrefKeys.APP_THEME_NAME, "") ?: ""
        if (theme != lastKnownTheme) {
            lastKnownTheme = theme
            if (shouldRecreateOnSettingsChange()) {
                recreate(); return
            }
        }

        val scale = p.getFloat(PrefKeys.APP_UI_SCALE, 1f)
        if (scale != lastKnownUiScale) {
            lastKnownUiScale = scale
            if (shouldRecreateOnSettingsChange()) {
                recreate(); return
            }
        }

        val preventRotation = p.getBoolean(PrefKeys.SYSTEM_PREVENT_ROTATION, false)
        applyRotationLock(preventRotation)

        ThemeManager.applyTheme(this, contentRoot())
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) applySystemBarVisibility()
    }

    // Public API

    protected open fun shouldRecreateOnSettingsChange(): Boolean = true

    fun applyCurrentTheme(root: View? = null) {
        ThemeManager.applyTheme(this, root ?: contentRoot())
        applyNavBarColor()
    }

    fun refreshFont() {
        FontManager.applyFont(this, contentRoot())
    }

    fun applyRotationLock(lock: Boolean) {
        requestedOrientation =
            if (lock) ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            else ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    protected fun applyEdgeToEdgePadding(root: View) {
        val paddingInline = dpToPx(this, 16f)
        val paddingBlock = dpToPx(this, 8f)

        root.setOnApplyWindowInsetsListener { view, insets ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val sysBars = insets.getInsets(
                    WindowInsets.Type.systemBars() or WindowInsets.Type.displayCutout()
                )
                val ime = insets.getInsets(WindowInsets.Type.ime())
                val bottomInset = if (insets.isVisible(WindowInsets.Type.ime())) ime.bottom
                else sysBars.bottom
                view.setPadding(
                    sysBars.left + paddingInline,
                    sysBars.top,
                    sysBars.right + paddingInline,
                    bottomInset + paddingBlock,
                )
            } else {
                @Suppress("DEPRECATION")
                view.setPadding(
                    insets.systemWindowInsetLeft + paddingInline,
                    paddingBlock, // insets.systemWindowInsetTop
                    insets.systemWindowInsetRight + paddingInline,
                    insets.systemWindowInsetBottom + paddingBlock
                )
            }
            insets
        }
    }

    // Private helpers

    protected fun applyNavBarColor() {
        val theme = rawPrefs(this).getString(PrefKeys.APP_THEME_NAME, "") ?: ""
        val palette = ThemeManager.paletteFor(theme, this)
        window.navigationBarColor = palette.bg_1
    }

    protected open fun isSystemBarVisible(): Boolean {
        return rawPrefs(this)
            .getBoolean(PrefKeys.SYSTEM_BAR_VISIBLE, true)
    }

    private fun applySystemBarVisibility() {
        if (isSystemBarVisible()) {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
        contentRoot().requestApplyInsets()
    }

    private fun contentRoot(): View = findViewById(android.R.id.content)

    private fun rawPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)
}