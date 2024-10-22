package com.anasmohammed.qiblatalmuslim.f00Core.helpers.navigation

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

/** Navigate **/
fun AppCompatActivity.navigateToActivity(
    destinationActivity: Class<*>,
    bundleExtras: Map<String, Any>? = null,
    finishAfterStart: Boolean = false
) {
    val intent = Intent(this, destinationActivity).apply {
        bundleExtras?.forEach {
            //string
            if (it.value is String) {
                putExtra(it.key, it.value.toString())
            }
            //int
            if (it.value is Int) {
                putExtra(it.key, it.value as Int)
            }
            //boolean
            if (it.value is Boolean) {
                putExtra(it.key, it.value as Boolean)
            }
            //long
            if (it.value is Long) {
                putExtra(it.key, it.value as Long)
            }
            //Parcelable
            if (it.value is Parcelable) {
                putExtra(it.key, it.value as Parcelable)
            }
        }
    }
    startActivity(intent)
    if (finishAfterStart) finish()
}

/** Navigate With Bundle **/

fun Fragment.navigateToWithBundle(
    destination: Int,
    bundleExtras: Map<String, Any>,
    clearStack: Boolean = false,
    popBackDestination: Int = 0
) {
    val bundle = Bundle().apply {
        bundleExtras.forEach {
            //string
            if (it.value is String) {
                putString(it.key, it.value.toString())
            }
            //int
            if (it.value is Int) {
                putInt(it.key, it.value as Int)
            }
            //boolean
            if (it.value is Boolean) {
                putBoolean(it.key, it.value as Boolean)
            }
            //long
            if (it.value is Long) {
                putLong(it.key, it.value as Long)
            }
            //long
            if (it.value is Double) {
                putDouble(it.key, it.value as Double)
            }
            //Parcelable
            if (it.value is Parcelable) {
                putParcelable(it.key, it.value as Parcelable)
            }
        }
    }

    if (clearStack && popBackDestination != 0) {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(findNavController().graph.startDestinationId, false)
            .build()
        findNavController().navigate(destination, bundle, navOptions)
        return
    }

    findNavController().navigate(destination, bundle)
}