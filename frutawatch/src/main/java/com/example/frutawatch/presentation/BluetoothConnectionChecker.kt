package com.example.frutawatch.presentation

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class BluetoothConnectionChecker(private val context: Context) {

    fun isWatchDisconnected(): Boolean {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        return if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH)
            == PackageManager.PERMISSION_GRANTED) {
            bluetoothAdapter == null || !bluetoothAdapter.isEnabled || bluetoothAdapter.bondedDevices.isEmpty()
        } else {
            false
        }
    }
}
