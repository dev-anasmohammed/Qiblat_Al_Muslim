package com.anasmohammed.qiblatalmuslim.f00Core.extensions

import android.widget.TextView
import androidx.annotation.StringRes

fun TextView.setResText(@StringRes value: Int) {
    text = context.getString(value)
}

fun TextView.setResText(@StringRes resource: Int, vararg values: Any) {
    text = context.getString(resource, *values)
}