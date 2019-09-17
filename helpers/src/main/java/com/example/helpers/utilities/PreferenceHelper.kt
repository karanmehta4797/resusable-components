package com.example.helpers.utilities

import android.content.Context
import android.content.SharedPreferences
import com.example.helpers.BuildConfig

class PreferenceHelper {

    companion object {

        private lateinit var sharedPreferences: SharedPreferences

        //To open instance of shared preference
        private fun openPreference(context: Context) {
            sharedPreferences =
                context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
        }

        //To set string value in shared preference
        fun setStringValue(context: Context, key: String, value: String) {
            openPreference(context)
            sharedPreferences.edit().putString(key, value).commit()
        }

        //To get string value from shared preference
        fun getStringValue(context: Context, key: String, defaultValue: String): String {
            openPreference(context)
            var value: String = sharedPreferences.getString(key, defaultValue)
            return value
        }

        //To set integer value in shared preference
        fun setIntValue(context: Context, key: String, value: Int) {
            openPreference(context)
            sharedPreferences.edit().putInt(key, value).commit()
        }

        //To get shared preference from shared preference
        fun getIntValue(context: Context, key: String, defaultValue: Int): Int {
            openPreference(context)
            var value: Int = sharedPreferences.getInt(key, defaultValue)
            return value
        }

        //To set boolean value in shared preference
        fun setBooleanValue(context: Context, key: String, value: Boolean) {
            openPreference(context)
            sharedPreferences.edit().putBoolean(key, value).commit()
        }

        //To get boolean value from shared preference
        fun getBooleanValue(context: Context, key: String, defaultValue: Boolean): Boolean {
            openPreference(context)
            var value: Boolean = sharedPreferences.getBoolean(key, defaultValue)
            return value
        }
    }
}