package com.anasmohammed.qiblatalmuslim.f00Gateways.aladhan.domain.model

import androidx.annotation.DrawableRes

data class PrayerTime(
    val id: Int,
    val gregorianDayDate: String,
    val name: String,
    val time: String,
    @DrawableRes val iconRes: Int
)