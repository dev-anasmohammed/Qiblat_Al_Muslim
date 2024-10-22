package com.anasmohammed.qiblatalmuslim.f01Splash.presentation.view

import android.annotation.SuppressLint
import android.os.Bundle
import com.anasmohammed.qiblatalmuslim.MainActivity
import com.anasmohammed.qiblatalmuslim.databinding.ActivitySplashBinding
import com.anasmohammed.qiblatalmuslim.f00Core.bases.BaseActivity
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.navigation.navigateToActivity
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.runAfterDelay
import com.anasmohammed.qiblatalmuslim.f00Core.utilities.screen.setFullScreen
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun onCreateExtra(savedInstanceState: Bundle?) {
        super.onCreateExtra(savedInstanceState)
        setFullScreen(isEnabled = true)
        runAfterDelay(duration = 4000) {
            navigateToActivity(
                destinationActivity = MainActivity::class.java,
                finishAfterStart = true
            )
        }
    }
}