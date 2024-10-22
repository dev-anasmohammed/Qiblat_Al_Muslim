package com.anasmohammed.qiblatalmuslim.f03QiblaMap.presentation.view

import android.animation.ValueAnimator
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.anasmohammed.qiblatalmuslim.R
import com.anasmohammed.qiblatalmuslim.databinding.FragmentQiblaMapBinding
import com.anasmohammed.qiblatalmuslim.f00Core.bases.BaseFragment
import com.anasmohammed.qiblatalmuslim.f00Core.bases.handleUiState
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.getPointsBetweenTwoLatLng
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.marker.MapMarkerRippleHelper
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.polyline.createAnimatedPolyLine
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.initGoogleMap
import com.anasmohammed.qiblatalmuslim.f00Core.utilities.screen.setUserTouches
import com.anasmohammed.qiblatalmuslim.f03QiblaMap.presentation.helpers.addMapMarker
import com.anasmohammed.qiblatalmuslim.f03QiblaMap.presentation.helpers.setMapCameraListeners
import com.anasmohammed.qiblatalmuslim.f03QiblaMap.presentation.helpers.setupMapPreferences
import com.anasmohammed.qiblatalmuslim.f03QiblaMap.presentation.helpers.startIntro
import com.anasmohammed.qiblatalmuslim.f03QiblaMap.presentation.viewModel.QiblaMapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

/**
 * The user can view the Qibla direction on the map based on his location and device orientation
 * after the intro animation ended.
 */

private var lastUpdateTime: Long = 0
private const val updateInterval: Long = 50 // Update every 50 milliseconds
private var currentAzimuth: Float = 0f
private const val azimuthThreshold: Float = 2f // Reduced threshold to trigger rotation

@AndroidEntryPoint
class QiblaMapFragment : BaseFragment<FragmentQiblaMapBinding>(), OnMapReadyCallback,
    SensorEventListener {

    private lateinit var googleMap: GoogleMap
    private val viewModel: QiblaMapViewModel by viewModels()
    private val kaabaMarkerRippleHelper by lazy {
        MapMarkerRippleHelper(requireContext())
    }

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var magnetometer: Sensor? = null

    private val gravity = FloatArray(3)
    private val geomagnetic = FloatArray(3)
    private var azimuth: Float = 0f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //google map
        initGoogleMap(R.id.map_fragment, this)

        //get user location from homeFragment
        viewModel.userLocation.value = LatLng(
            arguments?.getDouble("latitude") ?: 0.0,
            arguments?.getDouble("longitude") ?: 0.0
        )

        //get qibla direction relative to the geographic North
        //this give the user the initial right orientation after that we can use the live updates
        viewModel.getQiblaDirections(
            latitude = viewModel.userLocation.value.latitude,
            longitude = viewModel.userLocation.value.longitude
        )

        //observers
        observeGetQiblaDirection()

        sensorManager =
            requireContext().getSystemService(SensorManager::class.java) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onDestroyView() {
        setUserTouches(isEnabled = true)
        super.onDestroyView()
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0

        //style , settings , zoom
        googleMap.setupMapPreferences(requireContext())

        //markers
        googleMap.addMapMarker(
            requireContext(),
            viewModel.kaabaLocation.value,
            R.drawable.ic_kaaba_marker
        )
        googleMap.addMapMarker(
            requireContext(),
            viewModel.userLocation.value,
            R.drawable.ic_user_marker
        )

        //camera
        googleMap.setMapCameraListeners(kaabaMarkerRippleHelper)
    }

    private fun observeGetQiblaDirection() {
        viewModel.qiblaDirectionResponse.observeInLifecycle { uiState ->
            handleUiState(uiState,
                onSuccessSingleTime = { response ->
                    viewModel.qiblaDirection.value = (response?.direction ?: 0.0).toFloat()
                    if (viewModel.qiblaDirection.value != 0f && viewModel.userPhoneDirection.value != 0f) {
                        startMapIntro()
                    }
                },
                onErrorSingleTime = {

                }
            )
        }
    }

    private fun startMapIntro() {
        //draw polyline
        val backgroundPolyline = googleMap.createAnimatedPolyLine(
            requireContext(),
            getPointsBetweenTwoLatLng(viewModel.userLocation.value, viewModel.kaabaLocation.value),
            lineColor = R.color.primaryColor,
            isGradient = false,
            lineWidth = 10f,
            isLineDotted = false,
            makeItOnce = true
        )

        val foregroundPolyline = googleMap.createAnimatedPolyLine(
            requireContext(),
            getPointsBetweenTwoLatLng(viewModel.userLocation.value, viewModel.kaabaLocation.value),
            lineColor = R.color.primaryColor,
            isGradient = true,
            isLineDotted = false,
            lineWidth = 12f
        )

        //Relative Qibla Direction=(Qibla Direction−Current Heading+360)
        startIntro(
            binding,
            viewModel,
            kaabaMarkerRippleHelper,
            viewModel.kaabaLocation.value,
            viewModel.userLocation.value,
            (viewModel.userPhoneDirection.value - viewModel.qiblaDirection.value + 360f),
            lifecycleScope,
            googleMap,
            backgroundPolyline,
            foregroundPolyline,
        )
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                System.arraycopy(event.values, 0, gravity, 0, event.values.size)
            }

            Sensor.TYPE_MAGNETIC_FIELD -> {
                System.arraycopy(event.values, 0, geomagnetic, 0, event.values.size)
            }
        }

        val r = FloatArray(9)
        val i = FloatArray(9)
        if (SensorManager.getRotationMatrix(r, i, gravity, geomagnetic)) {
            val orientation = FloatArray(3)
            SensorManager.getOrientation(r, orientation)
            azimuth = Math.toDegrees(orientation[0].toDouble()).toFloat()
            azimuth = (azimuth + 360) % 360

            val currentTime = SystemClock.elapsedRealtime()
            if (currentTime - lastUpdateTime > updateInterval) {
                lastUpdateTime = currentTime

                // Check if the change in azimuth exceeds the threshold
                val azimuthDifference = abs(azimuth - currentAzimuth)
                if (azimuthDifference > azimuthThreshold) {
                    if (viewModel.userPhoneDirection.value == 0f) {
                        viewModel.userPhoneDirection.value = azimuth

                        if (viewModel.qiblaDirection.value != 0f && viewModel.userPhoneDirection.value != 0f) {
                            startMapIntro()
                        }
                    }

                    if (!viewModel.isIntroWorking.value) {
                        animateCameraRotation(azimuth)
                    }
                }
            }
        }
    }

    // Function to animate the camera rotation smoothly
    private fun animateCameraRotation(targetAzimuth: Float) {
        // Smoothly interpolate between the current and target azimuth
        val difference = targetAzimuth - currentAzimuth
        // Normalize the difference to the range [-180, 180]
        val normalizedDifference = (difference + 180) % 360 - 180

        // Create a ValueAnimator to smoothly interpolate the azimuth angle
        val animator =
            ValueAnimator.ofFloat(currentAzimuth, currentAzimuth + normalizedDifference).apply {
                duration = 30 // Shortened duration for a snappier response
                addUpdateListener { animation ->
                    val animatedValue = animation.animatedValue as Float
                    // Update the camera position with the new bearing
                    val cameraPosition = CameraPosition.Builder()
                        .target(googleMap.cameraPosition.target) // Keep the current target position
                        .zoom(googleMap.cameraPosition.zoom) // Keep the current zoom level
                        .bearing(animatedValue) // Update the bearing
                        .tilt(googleMap.cameraPosition.tilt) // Keep the current tilt level
                        .build()

                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                }
            }
        animator.start()
        // Update current azimuth
        currentAzimuth = targetAzimuth
    }
}