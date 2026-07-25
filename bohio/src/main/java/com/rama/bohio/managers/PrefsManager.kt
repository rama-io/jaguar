package com.rama.bohio.managers

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import org.json.JSONObject
import com.rama.bohio.objects.PrefFontStyle
import com.rama.bohio.objects.PrefKeys
import com.rama.bohio.objects.PrefLanguage
import com.rama.bohio.objects.PrefTheme

abstract class PrefsManager protected constructor(context: Context) {

    val prefs: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    protected open val defaultTheme: String = PrefTheme.RAMA

    fun getFontStyle(): String =
        prefs.getString(PrefKeys.FONT_STYLE, "") ?: ""

    fun setFontStyle(style: String) =
        prefs.edit().putString(PrefKeys.FONT_STYLE, style).apply()

    fun getCustomFontPath(): String =
        prefs.getString(PrefKeys.FONT_CUSTOM_PATH, "") ?: ""

    fun setCustomFontPath(path: String) =
        prefs.edit().putString(PrefKeys.FONT_CUSTOM_PATH, path).apply()

    fun getTheme(): String =
        prefs.getString(PrefKeys.APP_THEME_NAME, "") ?: ""

    fun setTheme(style: String) =
        prefs.edit().putString(PrefKeys.APP_THEME_NAME, style).apply()

    fun getCustomThemeColor(key: String, fallback: Int): Int =
        prefs.getInt(key, fallback)

    fun setCustomThemeColor(key: String, color: Int) =
        prefs.edit().putInt(key, color).apply()

    fun getAppLanguage(): String {
        return prefs.getString(PrefKeys.APP_LANGUAGE, PrefLanguage.SYSTEM) ?: PrefLanguage.SYSTEM
    }

    fun setAppLanguage(language: String) {
        prefs.edit().putString(PrefKeys.APP_LANGUAGE, language).apply()
    }

    fun getUiScale(): Float =
        prefs.getFloat(PrefKeys.APP_UI_SCALE, 1f)

    fun setUiScale(scale: Float) =
        prefs.edit().putFloat(PrefKeys.APP_UI_SCALE, scale)
            .apply()

    // Global Helpers

    fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        prefs.getBoolean(key, defaultValue)

    fun setBoolean(key: String, value: Boolean) =
        prefs.edit().putBoolean(key, value).apply()

    fun getString(key: String, defaultValue: String = ""): String =
        prefs.getString(key, defaultValue) ?: defaultValue

    fun setString(key: String, value: String) =
        prefs.edit().putString(key, value).apply()

    open fun initPrefs(sync: Boolean = false) {
        if (prefs.contains(PrefKeys.APP_LANGUAGE)) return

        val editor = prefs.edit()
            .putString(PrefKeys.FONT_STYLE, PrefFontStyle.JERSEY_25)
            .putString(PrefKeys.APP_LANGUAGE, PrefLanguage.SYSTEM)
            .putFloat(PrefKeys.APP_UI_SCALE, 1f)
            .putBoolean(PrefKeys.SYSTEM_BAR_VISIBLE, true)
            .putBoolean(PrefKeys.SYSTEM_PREVENT_ROTATION, false)
            .putString(PrefKeys.APP_THEME_NAME, defaultTheme)

            // Sections.Global
            .putBoolean(PrefKeys.SETTINGS_SECTION_FONTS, true)
            .putBoolean(PrefKeys.SETTINGS_SECTION_SYSTEM, true)
            .putBoolean(PrefKeys.SETTINGS_SECTION_LANGUAGE, true)
            .putBoolean(PrefKeys.SETTINGS_SECTION_THEMES, true)

        applyAppDefaults(editor)

        if (sync) editor.commit() else editor.apply()
    }

    protected open fun applyAppDefaults(editor: SharedPreferences.Editor) {
    }

    // Export/Import/Clear

    fun buildExportJson(): JSONObject {
        val json = JSONObject()

        val sortedEntries = prefs.all.entries
            .sortedBy { it.key }

        sortedEntries.forEach { (key, value) ->
            when (value) {
                is Boolean -> json.put(key, value)
                is Int -> json.put(key, value)
                is Long -> json.put(key, value)
                is Float -> json.put(key, value)
                is String -> json.put(key, value)

                is Set<*> -> {
                    val array = org.json.JSONArray()
                    value.forEach { item ->
                        array.put(item)
                    }
                    json.put(key, array)
                }

                else -> json.put(key, value.toString())
            }
        }

        return json
    }

    fun importFromUri(context: Context, uri: Uri): Boolean {
        return try {
            val jsonString = context.contentResolver.openInputStream(uri)?.use {
                it.bufferedReader().readText()
            } ?: return false

            val json = JSONObject(jsonString)

            clearAllPrefs()

            val editor = prefs.edit()
            json.keys().forEach { key ->
                when (val value = json.get(key)) {
                    is Boolean -> editor.putBoolean(key, value)
                    is Int -> {
                        if (key in FLOAT_PREF_KEYS) editor.putFloat(key, value.toFloat())
                        else editor.putInt(key, value)
                    }

                    is Long -> editor.putLong(key, value)
                    is Float -> editor.putFloat(key, value)
                    is Double -> editor.putFloat(key, value.toFloat())
                    is String -> editor.putString(key, value)
                    is org.json.JSONArray -> {
                        val set = mutableSetOf<String>()
                        for (i in 0 until value.length()) {
                            set.add(value.getString(i))
                        }
                        editor.putStringSet(key, set)
                    }
                }
            }
            editor.commit()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Export to SAF (user picked location)

    fun exportToUri(context: Context, uri: Uri): Boolean {
        return try {
            val json = buildExportJson()

            context.contentResolver.openOutputStream(uri)?.use {
                it.write(json.toString(2).toByteArray())
            }

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun clearAllPrefs(): Result<Unit> {
        return try {
            prefs.edit().clear().commit()
            initPrefs(sync = true)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    companion object {
        // Keys whose JSON-encoded integers must be re-coerced to Float on import
        private val FLOAT_PREF_KEYS = setOf(
            PrefKeys.APP_UI_SCALE
        )

        @Volatile
        private var instance: PrefsManager? = null

        fun register(prefs: PrefsManager) {
            instance = prefs
        }

        fun getInstance(context: Context): PrefsManager =
            instance ?: error(
                "PrefsManager.register() has not been called. " +
                        "Make sure your app's PrefsManager.getInstance() is called before " +
                        "FontManager or ThemeManager."
            )
    }
}
