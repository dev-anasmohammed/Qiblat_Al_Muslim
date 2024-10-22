package com.anasmohammed.qiblatalmuslim.f00Core.utilities.screen

import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment

fun Fragment.changeStatusBarColor(
    color: Int,
    isContentLight: Boolean,
) {
    //color
    requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), color)

    //content
    WindowCompat.getInsetsController(requireActivity().window, requireActivity().window.decorView)
        .isAppearanceLightStatusBars = !isContentLight

    WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)
}

