package com.example.resusablecomponents.utilities

import android.app.Activity
import androidx.core.app.ShareCompat

open class ShareAppHelper {

    companion object {

        //To show chooser for sharing app link
        fun showShareAppChooser(context: Activity, chooserTitle: String, text: String) {
            ShareCompat.IntentBuilder.from(context)
                .setType("text/plain")
                .setChooserTitle(chooserTitle)
                .setText(text)
                .startChooser()
        }
    }
}