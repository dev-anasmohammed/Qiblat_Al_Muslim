package com.anasmohammed.qiblatalmuslim.f00Core.helpers.time

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.Duration
import java.util.Date
import java.util.Locale

fun isSecondTimeBigger(firstTime: String, secondTime: String): Boolean {
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    try {
        val firstDate: Date = timeFormat.parse(firstTime) ?: return false
        val secondDate: Date = timeFormat.parse(secondTime) ?: return false

        return secondDate.after(firstDate)
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
}

fun getFormattedCurrentTime(): String {
    val currentTime = Date()
    val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return formatter.format(currentTime)
}


fun getRemainingTime(startTimeStr: String, endTimeStr: String): Long {
    if (startTimeStr.isEmpty() || endTimeStr.isEmpty()) return 0
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")

    val startTime = LocalTime.parse(startTimeStr, formatter)
    val endTime = LocalTime.parse(endTimeStr, formatter)

    val duration = Duration.between(endTime, startTime)

    return duration.toMillis()
}

@SuppressLint("DefaultLocale")
fun formatTimeTo12Hour(time: String): String {
    if (time.trim().isEmpty()) return ""
    val timeParts = time.trim().split(":")

    if (timeParts.size != 2) return "Invalid time"

    val hours = timeParts[0].toIntOrNull() ?: return "Invalid time"
    val minutes = timeParts[1].toIntOrNull() ?: return "Invalid time"

    val amPm = if (hours >= 12) "PM" else "AM"
    val formattedHours = if (hours % 12 == 0) 12 else hours % 12

    return String.format("%02d:%02d %s", formattedHours, minutes, amPm)
}