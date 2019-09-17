package com.example.helpers.utilities

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.util.DisplayMetrics
import android.view.WindowManager
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.content.Context.ACTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.app.ActivityManager
import android.util.Log
import androidx.annotation.RequiresApi


class BugReportHelper {

    companion object {

        var packageInfo: PackageInfo = PackageInfo()

        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        fun sendCrashReport(context: Context) {
            packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)

            var report = "App version : "+ getAppVersion()+"\n"
            report += "App code : "+ getAppCode()+"\n"
            report += "OS version : "+ getOsVersion()+"\n"
            report += "Device model : "+ getDeviceModel()+"\n"
            report += "Device dimensions : "+ getDeviceHeight(context)+" x "+ getDeviceWidth(context)+"\n"
            report += "Timestamp : "+ getTimeStamp()+"\n"
            report += "Total storage : "+ getTotalStorageSize()+"\n"
            report += "Available storage : "+ getAvailableStorageSize()+"\n"
            report += "Total RAM : "+ getTotalRamSize(context)+"\n"
            report += "Available RAM : "+ getAvailableRamSize(context)

            Log.e("ERROR_REPORT", report)
        }

        private fun createFolder(directory: String, folderName: String) {

            var file = File(directory, folderName)
            file.mkdir()
        }

        private fun createAndStoreFile() {

        }

        private fun getAppVersion(): String {

            return packageInfo.versionName
        }

        private fun getAppCode(): Int {

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode.toInt()
            } else {
                packageInfo.versionCode
            }
        }

        private fun getOsVersion(): String {

            return Build.VERSION.CODENAME
        }

        private fun getDeviceModel(): String {

            return Build.MANUFACTURER + " " + Build.MODEL
        }

        private fun getDeviceHeight(context: Context): Int {

            var displayMatrics = DisplayMetrics()
            var windowManager: WindowManager =
                context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(displayMatrics)

            return displayMatrics.heightPixels
        }

        private fun getDeviceWidth(context: Context): Int {

            var displayMatrics = DisplayMetrics()
            var windowManager: WindowManager =
                context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(displayMatrics)

            return displayMatrics.widthPixels
        }

        private fun getTimeStamp(): String {

            var simpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/mm/yyyy hh:mm:ss")
            return simpleDateFormat.format(Date())
        }

        private fun getTotalStorageSize(): Int {

            var statFs = StatFs(Environment.getExternalStorageDirectory().path)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                return statFs.blockSizeLong.toInt() * statFs.blockCountLong.toInt()
            } else {
                return statFs.blockSize * statFs.blockCount
            }
        }

        private fun getAvailableStorageSize(): Int {

            var statFs = StatFs(Environment.getExternalStorageDirectory().path)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                return statFs.blockSizeLong.toInt() * statFs.availableBlocksLong.toInt()
            } else {
                return statFs.blockSize * statFs.availableBlocks
            }
        }

        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        private fun getTotalRamSize(context: Context) : Long {

            val mi = ActivityManager.MemoryInfo()
            val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager?
            activityManager!!.getMemoryInfo(mi)
            return mi.totalMem / 1048576L
        }

        private fun getAvailableRamSize(context: Context) : Long {
            val mi = ActivityManager.MemoryInfo()
            val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager?
            activityManager!!.getMemoryInfo(mi)

            return mi.availMem / 1048576L
        }
    }
}