package com.example.helpers.utilities

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

class LanguageHelper {

    companion object {

        private lateinit var resources: Resources
        private var configuration: Configuration = Configuration()

        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        fun setLanguage(context: Context, language: String, preferenceKey: String) {

            val locale = Locale(language)
            Locale.setDefault(locale)
            resources = context.resources
            configuration = Configuration(resources.configuration)
            configuration.setLocale(locale)
            resources.updateConfiguration(configuration, resources.displayMetrics)
            //contextNew = context.createConfigurationContext(configuration)
            PreferenceHelper.setStringValue(context, preferenceKey, language)
        }
    }
}