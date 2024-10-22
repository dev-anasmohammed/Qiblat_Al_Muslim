package com.anasmohammed.qiblatalmuslim.f00Core.extensions

import android.os.SystemClock
import android.view.View

fun View.clickWithThrottle(throttleTime: Long = 500L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {

        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < throttleTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}
