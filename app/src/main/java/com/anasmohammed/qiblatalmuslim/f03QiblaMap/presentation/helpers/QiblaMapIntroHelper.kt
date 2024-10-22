package com.anasmohammed.qiblatalmuslim.f03QiblaMap.presentation.helpers

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import com.anasmohammed.qiblatalmuslim.databinding.FragmentQiblaMapBinding
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.polyline.AnimatedPolyline
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.marker.MapMarkerRippleHelper
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.animateCameraWithBounds
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.moveMapCamera
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.rotateCamera
import com.anasmohammed.qiblatalmuslim.f00Core.utilities.animations.animateAlpha
import com.anasmohammed.qiblatalmuslim.f00Core.utilities.animations.animateUpAndDown
import com.anasmohammed.qiblatalmuslim.f00Core.utilities.screen.setUserTouches
import com.anasmohammed.qiblatalmuslim.f03QiblaMap.presentation.viewModel.QiblaMapViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Fragment.startIntro(
    binding: FragmentQiblaMapBinding,
    viewModel: QiblaMapViewModel,
    kaabaMarkerRippleHelper: MapMarkerRippleHelper,
    kaabaLocation: LatLng,
    userLocation: LatLng,
    relativeQiblaDirection: Float,
    lifecycleScope: LifecycleCoroutineScope,
    googleMap: GoogleMap,
    backgroundPolyline: AnimatedPolyline?,
    foregroundPolyline: AnimatedPolyline?
) {
    lifecycleScope.launch(Dispatchers.Main) {

        //disable user input until intro finish
        setUserTouches(isEnabled = false)

        //zoom kaaba
        googleMap.moveMapCamera(
            target = kaabaLocation,
            zoom = 16f
        )

        //start kaaba ripple
        googleMap.startRippleMarker(kaabaMarkerRippleHelper, kaabaLocation)
        delay(1000)

        binding.meccaTv.translationY = 50f
        binding.kaabaTv.animateAlpha(0f, 1f, 200)

        delay(300)

        binding.meccaTv.animateAlpha(0f, 1f, 400)
        binding.meccaTv.animateUpAndDown(50f, 0f, 400)
        delay(2500)

        binding.kaabaTv.animateAlpha(1f, 0f, 150)
        binding.meccaTv.animateAlpha(1f, 0f, 300)
        delay(800)

        //animate camera to kaaba and user
        googleMap.animateCameraWithBounds(
            requireContext(), listOf(
                kaabaLocation,
                userLocation
            )
        )
        delay(2000)

        //draw polyline
        startAnimatePolyline(backgroundPolyline, foregroundPolyline)
        delay(2500)

        //rotate
        googleMap.rotateCamera(bearing = relativeQiblaDirection)

        //end intro and enable user input
        setUserTouches(isEnabled = true)
        delay(2000)

        viewModel.isIntroWorking.value = false
        foregroundPolyline?.remove()
    }
}