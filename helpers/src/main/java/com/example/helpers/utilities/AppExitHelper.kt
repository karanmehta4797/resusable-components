package com.example.helpers.utilities

import android.app.Activity
import android.app.AlertDialog

class AppExitHelper {

    companion object {

        //To show exit confirmation dialog when back pressed on any screen
        fun showAppExitDialog(context: Activity) {

            AlertDialog.Builder(context)
                .setTitle("Exit app")
                .setMessage("Do you want to exit?")
                .setPositiveButton("Yes") { dialog, which ->
                    context.finish()
                }
                .setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .show()
        }
    }
}