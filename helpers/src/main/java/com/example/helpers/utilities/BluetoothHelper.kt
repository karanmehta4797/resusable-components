package com.example.helpers.utilities

import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.util.Log

open class BluetoothHelper : PermissionHelper() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkBluetooth()
    }

    private fun checkBluetooth() {

        askPermission(this,
            arrayListOf(
                android.Manifest.permission.BLUETOOTH_ADMIN,
                android.Manifest.permission.BLUETOOTH
            ),
            object : PermissionCallback {
                override fun onPermissionGranted() {

                    val adapter = BluetoothAdapter.getDefaultAdapter()
                    if (adapter != null) {
                        if (BluetoothAdapter.getDefaultAdapter().isEnabled) {
                            Log.e("BLUETOOTH", "ENABLED")
                        } else {
                            Log.e("BLUETOOTH", "DISABLED")
                            adapter.enable()
                        }
                    } else {
                        Log.e("BLUETOOTH", "NOT AVAILABLE")
                    }
                }

                override fun onPermissionNotGranted() {
                    Log.e("BLUETOOTH", "PERMISSION NOT GRANTED")
                }

            })
    }
}