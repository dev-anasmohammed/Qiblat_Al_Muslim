package com.anasmohammed.qiblatalmuslim.f02Home.presentation.helpers

import android.annotation.SuppressLint
import com.anasmohammed.qiblatalmuslim.databinding.FragmentHomeBinding
import com.anasmohammed.qiblatalmuslim.f00Core.manager.CalendarManager
import com.anasmohammed.qiblatalmuslim.f00Core.manager.model.CalenderManagerDay
import com.anasmohammed.qiblatalmuslim.f02Home.presentation.adapters.DaysAdapter
import com.anasmohammed.qiblatalmuslim.f02Home.presentation.adapters.PrayerTimesAdapter
import com.anasmohammed.qiblatalmuslim.f02Home.presentation.viewModel.HomeViewModel

@SuppressLint("DefaultLocale")
fun FragmentHomeBinding.setTheCurrentMonthAndDays(
    viewModel: HomeViewModel,
    calendarManager: CalendarManager,
    daysAdapter: DaysAdapter,
    prayerTimesAdapter: PrayerTimesAdapter,
) {
    //current month and year
    currentMonthTv.text = calendarManager.getFormattedMonthYear()

    //clear old days
    daysAdapter.submitList(null)
    daysAdapter.resetIndex()

    if (calendarManager.isCurrentMonthAndYearReal()) {
        setTheCurrentMonthDays(
            calendarManager.getDaysList(fromCurrentDay = true),
            daysAdapter,
            prayerTimesAdapter,
            viewModel
        )
    } else {
        setTheCurrentMonthDays(
            calendarManager.getDaysList(fromCurrentDay = false),
            daysAdapter,
            prayerTimesAdapter,
            viewModel
        )
    }

    //collect date data for prayer times request
    viewModel.calenderPrayerTimesRequest.value.month =
        String.format("%02d", calendarManager.currentMonthYear.value.monthValue)
    viewModel.calenderPrayerTimesRequest.value.year =
        calendarManager.currentMonthYear.value.year.toString()

    //if location and date data collected get prayer times
    if (viewModel.calenderPrayerTimesRequest.value.isAllDataCollected) {
        viewModel.getCalenderPrayerTimes()
    }
}

fun FragmentHomeBinding.setTheCurrentMonthDays(
    daysList: List<CalenderManagerDay>,
    daysAdapter: DaysAdapter,
    prayerTimesAdapter: PrayerTimesAdapter,
    viewModel: HomeViewModel
) {
    //if empty
    if (daysList.isEmpty()) return

    //collect day
    viewModel.calenderPrayerTimesRequest.value.day = daysList.first().dayNumber

    //if not empty
    daysList.first().isSelected = true
    daysAdapter.submitList(daysList)
    daysRv.scheduleLayoutAnimation()

    daysAdapter.setItemClickListener { day ->
        val v = viewModel.calendarPrayerTimesResponse.value.data?.filter {
            it.gregorianDayDate == "${day.dayNumber}-${day.monthNumber}-${day.yearNumber}"
        }
        prayerTimesAdapter.submitList(null)
        prayerTimesAdapter.submitList(v)
        prayerTimesRv.scheduleLayoutAnimation()
    }
}