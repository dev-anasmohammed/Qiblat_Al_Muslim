package com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.marker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

fun setMarkerOptions(
    context: Context,
    latLng: LatLng,
    icon: Int,
    iconWidth: Int = 130,
    iconHeight: Int = 130,
    title: String = "",
): MarkerOptions {
    val bitmap = BitmapFactory.decodeResource(context.resources, icon)
    return MarkerOptions()
        .position(latLng)
        .title(title)
        .anchor(0.5f, 0.5f)
        .icon(
            BitmapDescriptorFactory.fromBitmap(
                getResizedBitmap(bitmap, iconHeight, iconWidth)//220 88
            )
        )
}

fun getResizedBitmap(bm: Bitmap, newHeight: Int, newWidth: Int): Bitmap {
    val width = bm.width
    val height = bm.height
    val scaleWidth = newWidth.toFloat() / width
    val scaleHeight = newHeight.toFloat() / height
    val matrix = Matrix()
    matrix.postScale(scaleWidth, scaleHeight)
    return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false)
}

