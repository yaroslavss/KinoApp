package com.yara.kinoapp.data.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceProvider(context: Context) {
    private val appContext = context.applicationContext
    private val preference: SharedPreferences =
        appContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    init {
        // save preferences on first run
        if (preference.getBoolean(KEY_FIRST_LAUNCH, false)) {
            preference.edit { putString(KEY_DEFAULT_SEARCH, DEFAULT_SEARCH) }
            preference.edit { putBoolean(KEY_FIRST_LAUNCH, false) }
        }
    }

    fun saveDefaultSearch(searchStr: String) {
        preference.edit { putString(KEY_DEFAULT_SEARCH, searchStr) }
    }

    fun getDefaultSearch(): String {
        return preference.getString(KEY_DEFAULT_SEARCH, DEFAULT_SEARCH) ?: DEFAULT_SEARCH
    }

    companion object {
        private const val PREFERENCES_NAME = "settings"

        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_DEFAULT_SEARCH = "default_search"
        private const val DEFAULT_SEARCH = "god"
    }
}