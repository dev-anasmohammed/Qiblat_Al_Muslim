package com.anasmohammed.qiblatalmuslim.f02Home.presentation.view

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.anasmohammed.qiblatalmuslim.BuildConfig
import com.anasmohammed.qiblatalmuslim.R
import com.anasmohammed.qiblatalmuslim.databinding.FragmentHomeBinding
import com.anasmohammed.qiblatalmuslim.f00Core.bases.BaseFragment
import com.anasmohammed.qiblatalmuslim.f00Core.bases.handleUiState
import com.anasmohammed.qiblatalmuslim.f00Core.extensions.clickWithThrottle
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.dataStore.DataStoreKeys.USER_ADDRESS_KEY
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.navigation.navigateToWithBundle
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.permission.requestLocationPermission
import com.anasmohammed.qiblatalmuslim.f00Core.helpers.toast.showErrorToast
import com.anasmohammed.qiblatalmuslim.f00Core.manager.CalendarManager
import com.anasmohammed.qiblatalmuslim.f00Core.utilities.location.LocationResultCallback
import com.anasmohammed.qiblatalmuslim.f00Core.utilities.location.LocationUtility
import com.anasmohammed.qiblatalmuslim.f00Core.utilities.screen.changeStatusBarColor
import com.anasmohammed.qiblatalmuslim.f00Core.utilities.screen.setFullScreen
import com.anasmohammed.qiblatalmuslim.f00Gateways.google.helper.getAddressData
import com.anasmohammed.qiblatalmuslim.f02Home.presentation.adapters.DaysAdapter
import com.anasmohammed.qiblatalmuslim.f02Home.presentation.adapters.PrayerTimesAdapter
import com.anasmohammed.qiblatalmuslim.f02Home.presentation.helpers.setNextPrayer
import com.anasmohammed.qiblatalmuslim.f02Home.presentation.helpers.setTheCurrentMonthAndDays
import com.anasmohammed.qiblatalmuslim.f02Home.presentation.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), LocationResultCallback {

    private val viewModel: HomeViewModel by viewModels()
    private val daysAdapter = DaysAdapter()
    private val prayerTimesAdapter = PrayerTimesAdapter()
    private val calendarManager = CalendarManager()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //status bar
        setFullScreen(isEnabled = true)
        changeStatusBarColor(R.color.white, true)

        lifecycleScope.launch(Dispatchers.Main) {
            // Wait for permission result
            val permissionGranted = requestLocationPermissionSuspend()

            if (permissionGranted) {
                // Get old address if found until get new address
                binding.locationTv.text = dataStoreHelper.get(USER_ADDRESS_KEY)

                withContext(Dispatchers.IO) {
                    // Get location of user
                    LocationUtility().getCurrentLocation(requireContext(), this@HomeFragment)
                }
            } else {
                requireActivity().finish()
                Toast.makeText(
                    requireContext(),
                    "Location permission denied",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        //observers
        observeGetPrayerTimes()
        observeGetAddressFromLocation()
        observeMonthAndYearChange()

        //set adapters
        binding.daysRv.adapter = daysAdapter
        binding.prayerTimesRv.adapter = prayerTimesAdapter

        //setup arrows
        binding.arrowNext.clickWithThrottle {
            calendarManager.nextMonth()
        }
        binding.arrowBack.clickWithThrottle {
            calendarManager.previousMonth()
        }

        //qibla on map
        binding.mapLottie.setOnClickListener {
            navigateToWithBundle(
                R.id.action_homeFragment_to_qiblaMapFragment,
                mapOf(
                    "latitude" to (viewModel.calenderPrayerTimesRequest.value.latitude),
                    "longitude" to (viewModel.calenderPrayerTimesRequest.value.longitude)
                )
            )
        }
    }

    private suspend fun requestLocationPermissionSuspend(): Boolean {
        return suspendCancellableCoroutine { continuation ->
            requestLocationPermission(
                onPermissionGranted = {
                    continuation.resume(true) // Permission granted
                },
                onPermissionRejected = {
                    continuation.resume(false) // Permission rejected
                }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.animateNextPrayer.value = true
    }

    override fun onPause() {
        super.onPause()
        viewModel.animateNextPrayer.value = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.animateNextPrayer.value = false
    }

    /** Observers **/
    @SuppressLint("SetTextI18n")
    private fun observeGetAddressFromLocation() {
        viewModel.getAddressFromLocationResponse.observeInLifecycle { uiState ->
            handleUiState(uiState, onSuccessSingleTime = { response ->
                //set new user address
                val addressData = getAddressData(response)
                binding.locationTv.text = "${addressData.route} St (${addressData.country})"

                //save user address
                lifecycleScope.launch(Dispatchers.IO) {
                    dataStoreHelper.save(
                        USER_ADDRESS_KEY,
                        "${addressData.route} St (${addressData.country})"
                    )
                }
            }, onErrorSingleTime = {
                //just display , no need to show error msg
                binding.locationTv.text = "Unknown"
            })
        }
    }

    private fun observeMonthAndYearChange() {
        calendarManager.currentMonthYear.observeInLifecycle {
            binding.setTheCurrentMonthAndDays(
                viewModel, calendarManager, daysAdapter, prayerTimesAdapter
            )
        }
    }

    private fun observeGetPrayerTimes() {
        viewModel.calendarPrayerTimesResponse.observeInLifecycle { uiState ->
            handleUiState(
                uiState,
                onLoading = {
                    binding.prayerTimesLoadingLottie.visibility = View.VISIBLE
                },
                onSuccessSingleTime = { response ->
                    //hide loading
                    binding.prayerTimesLoadingLottie.visibility = View.GONE

                    with(viewModel.calenderPrayerTimesRequest.value) {
                        val firstDay = response?.filter {
                            it.gregorianDayDate == "${day}-${month}-${year}"
                        }

                        //save today prayers time for next prayer
                        if (viewModel.todayPrayerTimes.value.isEmpty()) {
                            viewModel.todayPrayerTimes.value.addAll(firstDay ?: listOf())
                        }

                        binding.setNextPrayer(viewModel)

                        prayerTimesAdapter.submitList(null)
                        prayerTimesAdapter.submitList(firstDay)
                    }
                }, onErrorSingleTime = {
                    showErrorToast(
                        it ?: getString(R.string.failed_to_get_prayer_times_please_try_again_later)
                    )
                })
        }
    }

    /** Location Request Result **/
    override fun onLocationRetrieved(locationInfo: Location) {
        //get address from location
        viewModel.getAddressFromLocation(
            "${locationInfo.latitude},${locationInfo.longitude}",
            "en",
            BuildConfig.MAP_API_KEY
        )

        //collect location data for prayer times request
        viewModel.calenderPrayerTimesRequest.value.latitude = locationInfo.latitude
        viewModel.calenderPrayerTimesRequest.value.longitude = locationInfo.longitude

        //if location and date data collected get prayer times
        if (viewModel.calenderPrayerTimesRequest.value.isAllDataCollected) {
            viewModel.getCalenderPrayerTimes()
        }
    }

    override fun onLocationFetchFailed(exception: Exception) {

    }
}