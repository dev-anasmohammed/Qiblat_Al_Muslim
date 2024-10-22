package com.anasmohammed.qiblatalmuslim.f00Core.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.getResourceColor(@ColorRes color: Int): Int = ContextCompat.getColor(this, color)