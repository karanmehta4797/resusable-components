package com.example.helpers.utilities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

open class PermissionHelper : AppCompatActivity() {

    companion object {

        private val REQUEST_PERMISSION = 100
        private var context = Activity()
        private var permissions: ArrayList<String>? = null
        private var callback: PermissionCallback? = null
        private var nonGrantedPermissions = ArrayList<String>()

        fun askPermission(
            context: Activity,
            permission: ArrayList<String>,
            callback: PermissionCallback
        ) {
            initialize(context, permission, callback)
            isNonGrantedPermissionExists()
            checkPermission()
        }

        fun initialize(
            context: Activity,
            permission: ArrayList<String>,
            callback: PermissionCallback
        ) {
            Companion.context = context
            permissions = permission
            Companion.callback = callback
        }

        private fun checkPermission() {
            if (isNonGrantedPermissionExists()) {
                if (shouldShowPermissionRationale()) {
                    showDialog()
                } else if (checkIfUserDeniedBefore()) {
                    openSettingsForPermission()
                } else {
                    askPermission()
                }
                updateAllAsAskedOnce()
            } else {
                if (callback != null) {
                    callback!!.onPermissionGranted()
                } else {
                    callback!!.onPermissionNotGranted()
                }
            }
        }

        private fun openSettingsForPermission() {
            AlertDialog.Builder(context)
                .setPositiveButton("Ok") { dialog, which ->
                    dialog.dismiss()

                    var intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.packageName, null)
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
                .setCancelable(false)
                .setTitle("Permission required")
                .setMessage(
                    "Please enable permission from settings. Without permissions app is unable to do task."
                )
                .show()
        }

        private fun shouldShowPermissionRationale(): Boolean {
            var flag = false
            nonGrantedPermissions.clear()

            for (permission in permissions!!) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                    flag = true
                    nonGrantedPermissions.add(permission)
                }
            }

            return flag
        }

        private fun showDialog() {
            AlertDialog.Builder(context)
                .setPositiveButton("Ok") { dialog, which ->
                    dialog.dismiss()
                    askPermission()
                }
                .setCancelable(false)
                .setTitle("Permission is required")
                .setMessage("User permission is required to use certain features, we will ask for permission again please provide it")
                .show()
        }

        private fun askPermission() {
            if (isNonGrantedPermissionExists()) {
                ActivityCompat.requestPermissions(
                    context,
                    nonGrantedPermissions.toTypedArray(),
                    REQUEST_PERMISSION
                )
            }
        }

        private fun isNonGrantedPermissionExists(): Boolean {
            nonGrantedPermissions.clear()
            var flag = false
            for (permission in permissions!!) {

                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    flag = true
                    nonGrantedPermissions.add(permission)
                }
            }
            return flag
        }

        private fun checkIfUserDeniedBefore(): Boolean {
            var flag = false
            nonGrantedPermissions.clear()

            for (permission in permissions!!) {
                if (PreferenceHelper.getBooleanValue(context, permission, false)) {
                    flag = true
                    nonGrantedPermissions.add(permission)
                }
            }
            return flag
        }

        private fun updateAllAsAskedOnce() {
            for (permission in permissions!!) {
                PreferenceHelper.setBooleanValue(context, permission, true)
            }
        }

        private fun updateNonGrantedPermission(grantResults: IntArray) {
            nonGrantedPermissions.clear()

            var i = 0
            while (i < grantResults.size) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    nonGrantedPermissions.add(permissions!![i])
                } else {
                    PreferenceHelper.setBooleanValue(context, permissions!![i], false)
                }
                i++
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_PERMISSION) {
            updateNonGrantedPermission(grantResults)

            if (nonGrantedPermissions.isEmpty()) {
                callback!!.onPermissionGranted()
            }
        }
    }
}
