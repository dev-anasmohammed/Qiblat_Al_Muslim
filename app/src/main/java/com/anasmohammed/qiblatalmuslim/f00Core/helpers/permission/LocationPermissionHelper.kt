package com.anasmohammed.qiblatalmuslim.f00Core.helpers.permission

import android.Manifest
import androidx.fragment.app.Fragment
import com.anasmohammed.qiblatalmuslim.R
import com.permissionx.guolindev.PermissionX

fun Fragment.requestLocationPermission(
    onPermissionGranted: (() -> Unit)? = null,
    onPermissionRejected: (() -> Unit)? = null,
) {
    PermissionX.init(this).permissions(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    ).onExplainRequestReason { _, _ ->
    }.onForwardToSettings { scope, deniedList ->
        scope.showForwardToSettingsDialog(
            deniedList,
            getString(R.string.location_permission_settings),
            getString(R.string.ok),
            getString(R.string.cancel)
        )
    }.request { allGranted, _, _ ->
        if (allGranted) {
            onPermissionGranted?.invoke()
        } else {
            onPermissionRejected?.invoke()
        }
    }
}