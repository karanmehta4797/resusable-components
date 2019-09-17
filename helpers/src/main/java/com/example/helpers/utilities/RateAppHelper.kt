package com.example.helpers.utilities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

open class RateAppHelper {

    companion object {

        //To show default rate app dialog
        fun showRateAppDialog(context: Context, appURL: String) {

            AlertDialog.Builder(context)
                .setTitle("Rate us")
                .setMessage("Would you like to rate us?")
                .setPositiveButton("Yes") { dialog, which ->
                    showPlayStoreIntent(context, appURL)
                    Toast.makeText(context, "Ok", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .show()
        }

        //To show rate app dialog with custom message
        fun showRateAppDialog(context: Context, appURL: String, title: String, message: String) {

            AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes") { dialog, which ->
                    showPlayStoreIntent(context, appURL)
                    Toast.makeText(context, "Ok", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .show()
        }

        //To open play store link for app
        private fun showPlayStoreIntent(context: Context, url: String) {
            var intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        }
    }
}