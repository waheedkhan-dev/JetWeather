package com.wk.weatherwise.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.wk.weatherwise.data.datasource.local.entities.CurrentWeatherEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object CommonFunctions {

    fun convertTimestampToUTCFormat(timestamp: Long): String {
        // Convert seconds to milliseconds
        val date = Date(timestamp * 1000)

        // Create a SimpleDateFormat instance for UTC
        val sdf = SimpleDateFormat("E, dd MMMM", Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        // Format the date
        return sdf.format(date)
    }

    fun convertTimestampToDayName(timestamp: Long): String {
        // Convert seconds to milliseconds
        val date = Date(timestamp * 1000)

        // Create a SimpleDateFormat instance for the full day name
        val sdf = SimpleDateFormat("EEEE", Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        // Format the date to get the full day name
        return sdf.format(date)
    }


    fun getCurrentWeather(): CurrentWeatherEntity {
        return CurrentWeatherEntity(
            dt = 1726899082,
            id = 1184249,
            feelsLike = 35.86,
            grndLevel = 962,
            humidity = 32,
            pressure = 1006,
            seaLevel = 1006,
            temp = 35.49,
            tempMax = 35.49,
            tempMin = 35.49,
            main = "Clear",
            description = "clear sky",
            icon = "01d",
            deg = 127,
            gust = 1.43,
            speed = 1.4,
            cityName = "Attock"
        )
    }

    fun windDirection(degrees: Double): String {
        return when {
            degrees < 22.5 || degrees >= 337.5 -> "N"
            degrees < 67.5 -> "NE"
            degrees < 112.5 -> "E"
            degrees < 157.5 -> "SE"
            degrees < 202.5 -> "S"
            degrees < 247.5 -> "SW"
            degrees < 292.5 -> "W"
            degrees < 337.5 -> "NW"
            else -> "N" // Fallback, should not reach here
        }
    }

    fun showPermissionDialog(context: Context, showCityNameDialog: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle("Go to Settings and Enable Location Permission")
            .setMessage(
                "We need access to your location to provide accurate weather updates for your current location. " +
                        "Without this permission, the app cannot show weather information based on your location." +
                        " or enter your city name manually."
            )
            .setCancelable(false)
            .setPositiveButton("Allow") { _, _ ->
                // Navigate to app settings
                openAppSettings(context = context)
            }.setNegativeButton("Don't Allow") { dialog, _ ->
                dialog.dismiss()
                showCityNameDialog()
            }.show()

    }

    private fun openAppSettings(context: Context) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }


    fun showEnableLocationDialog(context: Context, enableLocationRequest: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle("Location Required")
            .setMessage("Please enable location services.")
            .setPositiveButton("Enable Location") { _, _ ->
                enableLocationRequest()
            }
            .setCancelable(false)
            .show()
    }
}