package com.anasmohammed.qiblatalmuslim.f00Core.manager

import android.annotation.SuppressLint
import com.anasmohammed.qiblatalmuslim.f00Core.manager.model.CalenderManagerDay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class CalendarManager {
    private val _currentMonthYear: MutableStateFlow<YearMonth> = MutableStateFlow(YearMonth.now())
    val currentMonthYear: StateFlow<YearMonth> get() = _currentMonthYear

    private val currentRealMonth = _currentMonthYear.value.month.value
    private val currentRealYear = _currentMonthYear.value.year

    fun nextMonth() {
        _currentMonthYear.value = _currentMonthYear.value.plusMonths(1)
    }

    fun previousMonth(limitToCurrentMonth: Boolean = true, onLimited: (() -> Unit)? = null) {
        if (limitToCurrentMonth) {
            if (currentRealMonth == _currentMonthYear.value.monthValue) {
                onLimited?.invoke()
                return
            }
        }
        _currentMonthYear.value = _currentMonthYear.value.minusMonths(1)
    }

    // Get formatted month and year (e.g., "October 2024")
    fun getFormattedMonthYear(pattern: String = "MMMM yyyy"): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return _currentMonthYear.value.format(formatter)
    }

    @SuppressLint("DefaultLocale")
    fun getDaysList(fromCurrentDay: Boolean = false): List<CalenderManagerDay> {
        val daysInMonth = _currentMonthYear.value.lengthOfMonth()
        val startDay = if (fromCurrentDay) LocalDate.now().dayOfMonth else 1
        val daysList = mutableListOf<CalenderManagerDay>()

        var idCounter = 1

        for (day in startDay..daysInMonth) {
            val date = LocalDate.of(_currentMonthYear.value.year, _currentMonthYear.value.month, day)
            val dayNumber = String.format("%02d", date.dayOfMonth)
            val dayName = date.dayOfWeek.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
            val monthNumber = String.format("%02d", date.monthValue)
            val monthName = date.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
            val yearNumber = date.year.toString()
            val dayDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE)

            daysList.add(
                CalenderManagerDay(
                    id = idCounter,
                    dayNumber = dayNumber,
                    dayName = dayName,
                    monthNumber = monthNumber,
                    monthName = monthName,
                    yearNumber = yearNumber,
                    dayDate = dayDate
                )
            )
            idCounter++
        }
        return daysList
    }

    fun isCurrentMonthAndYearReal() = currentRealYear == _currentMonthYear.value.year
            && currentRealMonth == _currentMonthYear.value.monthValue
}