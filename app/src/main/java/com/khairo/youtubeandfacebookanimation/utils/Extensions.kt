package com.khairo.youtubeandfacebookanimation.utils

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Base64
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress
import java.util.*

/**
 * these some extensions and functions for repeating using
 *
 */

suspend fun Int.toastSuspend(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    context.getString(this).toast(context, duration)
}

suspend fun String.toastSuspend(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    withContext(Dispatchers.Main) {
        Toast.makeText(context, this@toastSuspend, duration).apply { show() }
    }
}

fun Int.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast =
    context.getString(this).toast(context, duration)

fun String.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast =
    Toast.makeText(context, this, duration).apply { show() }

fun View.snack(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG) {
    snack(resources.getString(messageRes), length)
}

fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG) {
    val snack = Snackbar.make(this, message, length)
    snack.show()
}

fun Context.start(to: AppCompatActivity) {
    val intent = Intent(this, to::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
}

@JvmName("startAffinity1")
fun AppCompatActivity.startAffinity(to: AppCompatActivity) {
    val intent = Intent(this, to::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
    finishAffinity()
}

/**
 * Start new activity with one String extra
 *
 * @param to destination page
 * @param key key for the extra
 * @param value value for the extra
 */
fun Context.start(to: AppCompatActivity, key: String, value: String) {
    val intent = Intent(this, to::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        putExtra(key, value)
    }
    startActivity(intent)
}

fun startAffinity(context: AppCompatActivity, to: AppCompatActivity) {
    val intent = Intent(context, to::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
    context.finishAffinity()
}

fun finish(context: AppCompatActivity) {
    context.finish()
}

fun random(start: Int, end: Int): Int {
    require(start <= end) { "Illegal Argument" }
    return (start..end).random()
}

fun convertToString(bitmap: Bitmap): String {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream)
    val imageInByte = stream.toByteArray()
    return Base64.encodeToString(imageInByte, 0)
}

fun Activity.findNavController(@IdRes viewId: Int): NavController =
    Navigation.findNavController(this, viewId)

fun Fragment.navigate(resId: Int, bundle: Bundle? = null) {
    NavHostFragment.findNavController(this).navigate(resId, bundle)
}

fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let {
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun isOnline(): Boolean =
    try {
        val timeoutMs = 1500
        val socket = Socket()
        val socketAddress: SocketAddress = InetSocketAddress("8.8.8.8", 53)
        socket.connect(socketAddress, timeoutMs)
        socket.close()
        true
    } catch (e: IOException) {
        false
    }

fun Context.dpToPx(dp: Int): Int = (dp * resources.displayMetrics.density).toInt()

fun DialogFragment.transparentBachGround() {
    dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
}

fun Context.getAspectRatio(): Float {
    val metrics = resources.displayMetrics
    return if (metrics.widthPixels.toFloat() > metrics.heightPixels.toFloat()) metrics.widthPixels.toFloat() / metrics.heightPixels.toFloat()
    else metrics.heightPixels.toFloat() / metrics.widthPixels.toFloat()
}

fun FragmentManager.findNavController(viewId: Int) =
    (findFragmentById(viewId) as NavHostFragment).navController

inline fun getValueAnimator(
    forward: Boolean = true,
    duration: Long,
    interpolator: TimeInterpolator,
    crossinline updateListener: (progress: Float) -> Unit
): ValueAnimator {
    val a =
        if (forward) ValueAnimator.ofFloat(0f, 1f)
        else ValueAnimator.ofFloat(1f, 0f)
    a.addUpdateListener { updateListener(it.animatedValue as Float) }
    a.duration = duration
    a.interpolator = interpolator
    return a
}

fun blendColors(color1: Int, color2: Int, ratio: Float): Int {
    val inverseRatio = 1f - ratio

    val a = (Color.alpha(color1) * inverseRatio) + (Color.alpha(color2) * ratio)
    val r = (Color.red(color1) * inverseRatio) + (Color.red(color2) * ratio)
    val g = (Color.green(color1) * inverseRatio) + (Color.green(color2) * ratio)
    val b = (Color.blue(color1) * inverseRatio) + (Color.blue(color2) * ratio)
    return Color.argb(a.toInt(), r.toInt(), g.toInt(), b.toInt())
}

inline val Int.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()
inline val Float.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)

inline val Context.screenWidth: Int
    get() = Point().also { (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getSize(it) }.x
inline val View.screenWidth: Int
    get() = context!!.screenWidth

fun View.setScale(scale: Float) {
    this.scaleX = scale
    this.scaleY = scale
}