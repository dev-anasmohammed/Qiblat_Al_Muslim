package com.anasmohammed.qiblatalmuslim.f02Home.presentation.helpers

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import com.anasmohammed.qiblatalmuslim.databinding.FragmentHomeBinding
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.time.getFormattedCurrentTime
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.time.getRemainingTime
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.time.isSecondTimeBigger
import com.anasmohammed.qiblatalmuslim.f00Core.utilities.animations.animateAlpha
import com.anasmohammed.qiblatalmuslim.f00Core.utilities.animations.animateUpAndDown
import com.anasmohammed.qiblatalmuslim.f02Home.presentation.viewModel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private var countDownTimer: CountDownTimer? = null
private var animationJob: Job? = null

@SuppressLint("SetTextI18n")
fun FragmentHomeBinding.setNextPrayer(viewModel: HomeViewModel) {

    nextPrayerTitleTv.visibility = View.VISIBLE

    // Cancel any existing countdown timer
    countDownTimer?.cancel()

    // Get next prayer time
    val currentTime = getFormattedCurrentTime()
    val nextPrayerTime = viewModel.todayPrayerTimes.value.find {
        isSecondTimeBigger(currentTime, it.time)
    }

    if (nextPrayerTime == null) {
        nextPrayerNameTv.text = "Next Day Fajr"
        nextPrayerNameTv.showTextView(countDownTv)
        return
    }

    // Set name
    nextPrayerNameTv.text = nextPrayerTime.name

    // Set count down
    val remainingTime = getRemainingTime(nextPrayerTime.time, currentTime)
    startCountDown(remainingTime, countDownTv) {
        viewModel.animateNextPrayer.value = false
        nextPrayerNameTv.hideTextView(countDownTv)
        countDownTv.hideTextView(nextPrayerNameTv)

        nextPrayerNameTv.text = "${nextPrayerTime.name} Time"
    }

    // Cancel any existing animation job
    animationJob?.cancel()
    animationJob = CoroutineScope(Dispatchers.Main).launch {
        while (viewModel.animateNextPrayer.value) {
            nextPrayerNameTv.showTextView(countDownTv)
            delay(5000)
            nextPrayerNameTv.hideTextView(countDownTv)
            delay(250)

            countDownTv.showTextView(nextPrayerNameTv)
            delay(5000)
            countDownTv.hideTextView(nextPrayerNameTv)
            delay(250)
        }
    }
}

fun TextView.showTextView(anotherTextView: TextView) {
    //hide another
    anotherTextView.visibility = View.GONE

    // If visible return
    if (visibility == View.VISIBLE) return

    CoroutineScope(Dispatchers.Main).launch {
        alpha = 0f
        translationY = 20f
        visibility = View.VISIBLE

        animateAlpha(0f, 1f, 150)
        animateUpAndDown(20f, 0f, 150)
    }
}

fun TextView.hideTextView(anotherTextView: TextView) {
    //hide another
    anotherTextView.visibility = View.GONE

    // If hidden return
    if (visibility == View.GONE) return

    CoroutineScope(Dispatchers.Main).launch {
        animateAlpha(1f, 0f, 150)
        animateUpAndDown(0f, -20f, 150)
        delay(150)
        visibility = View.GONE
    }
}

@SuppressLint("DefaultLocale")
fun startCountDown(
    remainingTimeMillis: Long,
    countDownTv: TextView,
    doOnFinish: () -> Unit
) {
    countDownTimer = object : CountDownTimer(remainingTimeMillis, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val hours = (millisUntilFinished / (1000 * 60 * 60)) % 24
            val minutes = (millisUntilFinished / (1000 * 60)) % 60
            val seconds = (millisUntilFinished / 1000) % 60

            val formattedHours = String.format("%02d", hours)
            val formattedMinutes = String.format("%02d", minutes)
            val formattedSeconds = String.format("%02d", seconds)

            val formattedTime = "$formattedHours:$formattedMinutes:$formattedSeconds"
            countDownTv.text = formattedTime
        }

        override fun onFinish() {
            doOnFinish()
        }
    }.start()
}
