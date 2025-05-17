package com.paopeye.devshowcase.util

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.paopeye.kit.extension.orFalse

object LocationUtils {

    fun checkLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(
            AppCompatActivity.LOCATION_SERVICE
        ) as LocationManager?
        val providers = locationManager?.getProviders(true)
        val isGpsProvided = providers?.contains(LocationManager.GPS_PROVIDER).orFalse()
        val isNetworkProvided = providers?.contains(LocationManager.NETWORK_PROVIDER).orFalse()
        return isGpsProvided || isNetworkProvided
    }

    fun showLocationDisabledAlert(context: Context) {
        val alertDialog = MaterialAlertDialogBuilder(context)
            .setTitle("Location Services Disabled")
            .setMessage("Please enable Location Services for best experience")
            .setPositiveButton("Go to Settings") { _, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                context.startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }

}
