package com.anasmohammed.qiblatalmuslim.f00Core.helpers

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun AppCompatActivity.runAfterDelay(duration: Long, action: () -> Unit) {
    lifecycleScope.launch {
        delay(duration)
        action()
    }
}