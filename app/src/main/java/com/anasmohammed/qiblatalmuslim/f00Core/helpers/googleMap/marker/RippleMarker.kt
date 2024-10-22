package com.anasmohammed.qiblatalmuslim.f00Core.helpers.googleMap.marker

import android.animation.IntEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.anasmohammed.qiblatalmuslim.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.GroundOverlay
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow

class MapRippleMarker(
    private val mGoogleMap: GoogleMap,
    private var mLatLng: LatLng,
    context: Context
) {

    private var mPrevLatLng: LatLng
    private var mBackgroundImageDescriptor: BitmapDescriptor? = null //ripple image.
    private var mTransparency = 0.1f //transparency of image.

    @Volatile
    var mDistance = MutableStateFlow(100000.0)//distance to which ripple should be shown in metres
    private var mNumberOfRipples = 1 //number of ripples to show, max = 4
    private var mFillColor = Color.TRANSPARENT //fill color of circle
    private var mStrokeColor = Color.BLACK //border color of circle
    private var mStrokeWidth = 10 //border width of circle
    private var mDurationBetweenTwoRipples: Long = 4000 //in microseconds.
    private var mRippleDuration: Long = 12000 //in microseconds
    private val mAnimators: Array<ValueAnimator?>
    private val mHandlers: Array<Handler?>

    private var mGroundOverlays: Array<GroundOverlay?> = arrayOf()
    private val mBackground: GradientDrawable?

    /**
     * @return current state of animation
     */
    private var isAnimationRunning = false

    private var mContext: Context? = null

    /**
     * @param transparency sets transparency for background of circle
     */
    fun withTransparency(transparency: Float): MapRippleMarker {
        mTransparency = transparency
        return this
    }

    /**
     * @param latLng sets position for center of circle
     */
    fun withLatLng(latLng: LatLng): MapRippleMarker {
        mPrevLatLng = mLatLng
        mLatLng = latLng
        return this
    }

    /**
     * @param numberOfRipples sets count of ripples
     */
    fun withNumberOfRipples(numberOfRipples: Int): MapRippleMarker {
        var nor = numberOfRipples
        if (numberOfRipples > 4 || numberOfRipples < 1) {
            nor = 4
        }
        mNumberOfRipples = nor
        return this
    }

    /**
     * @param fillColor sets fill color
     */
    fun withFillColor(fillColor: Int): MapRippleMarker {
        mFillColor = fillColor
        return this
    }

    /**
     * @param strokeColor sets stroke color
     */
    fun withStrokeColor(strokeColor: Int): MapRippleMarker {
        mStrokeColor = strokeColor
        return this
    }

    @Deprecated("use {@link #withStrokeWidth(int)} instead")
    fun withStrokewidth(strokeWidth: Int) {
        mStrokeWidth = strokeWidth
    }

    /**
     * @param strokeWidth sets stroke width
     */
    fun withStrokeWidth(strokeWidth: Int): MapRippleMarker {
        mStrokeWidth = strokeWidth
        return this
    }

    /**
     * @param durationBetweenTwoRipples sets duration before pulse tick animation
     */
    fun withDurationBetweenTwoRipples(durationBetweenTwoRipples: Long): MapRippleMarker {
        mDurationBetweenTwoRipples = durationBetweenTwoRipples
        return this
    }

    /**
     * @param rippleDuration sets duration of ripple animation
     */
    fun withRippleDuration(rippleDuration: Long): MapRippleMarker {
        mRippleDuration = rippleDuration
        return this
    }

    private var mCircleOneRunnable = Runnable {
        mGroundOverlays[0] = mGoogleMap.addGroundOverlay(
            GroundOverlayOptions()
                .position(mLatLng, mDistance.value.toInt().toFloat())
                .transparency(mTransparency)
                .image(mBackgroundImageDescriptor!!)
        )
        startAnimation(0)
    }

    private var mCircleTwoRunnable = Runnable {
        mGroundOverlays[1] = mGoogleMap.addGroundOverlay(
            GroundOverlayOptions()
                .position(mLatLng, mDistance.value.toInt().toFloat())
                .transparency(mTransparency)
                .image(mBackgroundImageDescriptor!!)

        )
        startAnimation(1)
    }

    private var mCircleThreeRunnable = Runnable {
        mGroundOverlays[2] = mGoogleMap.addGroundOverlay(
            GroundOverlayOptions()
                .position(mLatLng, mDistance.value.toInt().toFloat())
                .transparency(mTransparency)
                .image(mBackgroundImageDescriptor!!)
        )
        startAnimation(2)
    }

    private var mCircleFourRunnable = Runnable {
        mGroundOverlays[3] = mGoogleMap.addGroundOverlay(
            GroundOverlayOptions()
                .position(mLatLng, mDistance.value.toInt().toFloat())
                .transparency(mTransparency)
                .image(mBackgroundImageDescriptor!!)
        )
        startAnimation(3)
    }

    init {
        mPrevLatLng = mLatLng
        mBackground =
            ContextCompat.getDrawable(context, R.drawable.bg_marker_ripple) as GradientDrawable?
        mAnimators = arrayOfNulls(4)
        mHandlers = arrayOfNulls(4)
        mGroundOverlays = arrayOfNulls(4)
        mContext = context


    }

    private fun startAnimation(numberOfRipple: Int) {
        val animator = ValueAnimator.ofInt(0, mDistance.value.toInt())
        animator.repeatCount = ValueAnimator.INFINITE
        animator.repeatMode = ValueAnimator.REVERSE
        animator.setDuration(2000)
        animator.setEvaluator(IntEvaluator())
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { valueAnimator ->
            val animated = valueAnimator.animatedValue as Int
            mGroundOverlays[numberOfRipple]?.setDimensions(animated.toFloat())
            if (mDistance.value - animated <= 10) {
                if (mLatLng !== mPrevLatLng) {
                    mGroundOverlays[numberOfRipple]?.position = mLatLng
                }
            }
        }
        animator.start()
        mAnimators[numberOfRipple] = animator
    }

    private fun setDrawableAndBitmap() {
        mBackground?.setColor(mFillColor)
        mBackground?.setStroke(UiUtil.dpToPx(mStrokeWidth.toFloat()), mStrokeColor)
        mBackgroundImageDescriptor = UiUtil.drawableToBitmapDescriptor(mBackground)
    }

    /**
     * Stops current animation if it running
     */
    fun stopRippleMapAnimation(): Boolean {
        return if (isAnimationRunning) {
            try {
                for (i in 0 until mNumberOfRipples) {
                    if (i == 0) {
                        mHandlers[i]?.removeCallbacks(mCircleOneRunnable)
                        mAnimators[i]?.cancel()
                        mGroundOverlays[i]?.isVisible = false
                        mGroundOverlays[i]?.remove()
                    }
                    if (i == 1) {
                        mHandlers[i]?.removeCallbacks(mCircleTwoRunnable)
                        mAnimators[i]?.cancel()
                        mGroundOverlays[i]?.isVisible = false
                        mGroundOverlays[i]?.remove()
                    }
                    if (i == 2) {
                        mHandlers[i]?.removeCallbacks(mCircleThreeRunnable)
                        mAnimators[i]?.cancel()
                        mGroundOverlays[i]?.isVisible = false
                        mGroundOverlays[i]?.remove()
                    }
                    if (i == 3) {
                        mHandlers[i]?.removeCallbacks(mCircleFourRunnable)
                        mAnimators[i]?.cancel()
                        mGroundOverlays[i]?.isVisible = false
                        mGroundOverlays[i]?.remove()
                    }
                }
                isAnimationRunning = false // Add this line to reset the flag
                true
            } catch (e: Exception) {
                //no need to handle it
                Toast.makeText(mContext, e.message.toString(), Toast.LENGTH_SHORT).show()
                true
            }
        } else false
    }

    /**
     * Starts animations
     */
    fun startRippleMapAnimation() {
        if (!isAnimationRunning) {
            setDrawableAndBitmap()
            for (i in 0 until mNumberOfRipples) {
                if (i == 0) {
                    mHandlers[i] = Handler()
                    mHandlers[i]?.postDelayed(mCircleOneRunnable, mDurationBetweenTwoRipples * i)
                }
                if (i == 1) {
                    mHandlers[i] = Handler()
                    mHandlers[i]?.postDelayed(mCircleTwoRunnable, mDurationBetweenTwoRipples * i)
                }
                if (i == 2) {
                    mHandlers[i] = Handler()
                    mHandlers[i]?.postDelayed(mCircleThreeRunnable, mDurationBetweenTwoRipples * i)
                }
                if (i == 3) {
                    mHandlers[i] = Handler()
                    mHandlers[i]?.postDelayed(mCircleFourRunnable, mDurationBetweenTwoRipples * i)
                }
            }
        }
        isAnimationRunning = true
    }
}