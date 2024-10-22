package com.anasmohammed.qiblatalmuslim.f00Core.helpers.toast

import android.app.Activity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.anasmohammed.qiblatalmuslim.R
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

/** Failed **/
fun Fragment.showErrorToast(message: String = "" , title: String = "",duration : Long= 8000) {
    requireActivity().failedToast(message , title, duration )
}

private fun Activity.failedToast(message: String = "" , title: String = "" , duration : Long= 8000) {
    MotionToast.createColorToast(
        this,
        title.ifEmpty { "Error" },
        message,
        MotionToastStyle.ERROR,
        40,
        duration,
        ResourcesCompat.getFont(this, R.font.dm_sans_medium)
    )
}