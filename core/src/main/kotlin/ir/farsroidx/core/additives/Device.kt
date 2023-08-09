@file:Suppress("ObsoleteSdkInt", "AnnotateVersionCheck", "unused")

package ir.farsroidx.core.additives

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat

// TODO: Device ========================================================================= Device ===

/**
 * Android: [1.0]
 * @since Android Api: [1]
 * @see Build.VERSION_CODES.BASE
 * */
fun isAndroidBase() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE)

/**
 * Android: [1.1]
 * @since Android Api: [2]
 * @see Build.VERSION_CODES.BASE_1_1
 * */
fun isAndroidPetitFour() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE_1_1)

/**
 * Android: [1.5]
 * @since Android Api: [3]
 * @see Build.VERSION_CODES.CUPCAKE
 * */
fun isAndroidCupcake() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE)

/**
 * Android: [1.6]
 * @since Android Api: [4]
 * @see Build.VERSION_CODES.DONUT
 * */
fun isAndroidDonut() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT)

/**
 * Android: [2.0]
 * @since Android Api: [5]
 * @see Build.VERSION_CODES.ECLAIR
 * */
fun isAndroidEclair() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR)

/**
 * Android: [2.0.1]
 * @since Android Api: [6]
 * @see Build.VERSION_CODES.ECLAIR_0_1
 * */
fun isAndroidEclair2() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_0_1)

/**
 * Android: [2.1]
 * @since Android Api: [7]
 * @see Build.VERSION_CODES.ECLAIR_MR1
 * */
fun isAndroidEclair3() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1)

/**
 * Android: [2.2.3]
 * @since Android Api: [8]
 * @see Build.VERSION_CODES.FROYO
 * */
fun isAndroidFroyo() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO)

/**
 * Android: [2.3.2]
 * @since Android Api: [9]
 * @see Build.VERSION_CODES.GINGERBREAD
 * */
fun isAndroidGingerbread() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)

/**
 * Android: [2.3.7]
 * @since Android Api: [10]
 * @see Build.VERSION_CODES.GINGERBREAD_MR1
 * */
fun isAndroidGingerbread2() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1)

/**
 * Android: [3.0]
 * @since Android Api: [11]
 * @see Build.VERSION_CODES.HONEYCOMB
 * */
fun isAndroidHoneycomb() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)

/**
 * Android: [3.1]
 * @since Android Api: [12]
 * @see Build.VERSION_CODES.HONEYCOMB_MR1
 * */
fun isAndroidHoneycomb2() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1)

/**
 * Android: [3.2.6]
 * @since Android Api: [13]
 * @see Build.VERSION_CODES.HONEYCOMB_MR2
 * */
fun isAndroidHoneycomb3() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)

/**
 * Android: [4.0.2]
 * @since Android Api: [14]
 * @see Build.VERSION_CODES.ICE_CREAM_SANDWICH
 * */
fun isAndroidIceCreamSandwich() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)

/**
 * Android: [4.0.4]
 * @since Android Api: [15]
 * @see Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
 * */
fun isAndroidIceCreamSandwich2() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)

/**
 * Android: [4.1.2]
 * @since Android Api: [16]
 * @see Build.VERSION_CODES.JELLY_BEAN
 * */
fun isAndroidJellyBean() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)

/**
 * Android: [4.2.2]
 * @since Android Api: [17]
 * @see Build.VERSION_CODES.JELLY_BEAN_MR1
 * */
fun isAndroidJellyBean2() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)

/**
 * Android: [4.3.1]
 * @since Android Api: [18]
 * @see Build.VERSION_CODES.JELLY_BEAN_MR2
 * */
fun isAndroidJellyBean3() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)

/**
 * Android: [4.4.4]
 * @since Android Api: [19]
 * @see Build.VERSION_CODES.KITKAT
 * */
fun isAndroidKitKat() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)

/**
 * Android: [4.4-W.2]
 * @since Android Api: [20]
 * @see Build.VERSION_CODES.KITKAT_WATCH
 * */
fun isAndroidKitKatWatch() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH)

/**
 * Android: [5.0.2]
 * @since Android Api: [21]
 * @see Build.VERSION_CODES.LOLLIPOP
 * */
fun isAndroidLollipop() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)

/**
 * Android: [5.1.1]
 * @since Android Api: [22]
 * @see Build.VERSION_CODES.LOLLIPOP_MR1
 * */
fun isAndroidLollipop2() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1)

/**
 * Android: [6.0.1]
 * @since Android Api: [23]
 * @see Build.VERSION_CODES.M
 * */
fun isAndroidMarshmallow() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)

/**
 * Android: [7.0]
 * @since Android Api: [24]
 * @see Build.VERSION_CODES.N
 * */
fun isAndroidNougat() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)

/**
 * Android: [7.1.2]
 * @since Android Api: [25]
 * @see Build.VERSION_CODES.N_MR1
 * */
fun isAndroidNougat2() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1)

/**
 * Android: [8.1]
 * @since Android Api: [26]
 * @see Build.VERSION_CODES.O
 * */
fun isAndroidOreo() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)

/**
 * Android: [8.2]
 * @since Android Api: [27]
 * @see Build.VERSION_CODES.O_MR1
 * */
fun isAndroidOreo2() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1)

/**
 * Android: [9]
 * @since Android Api: [28]
 * @see Build.VERSION_CODES.P
 * */
fun isAndroidPie() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)

/**
 * Android: [10]
 * @since Android Api: [29]
 * @see Build.VERSION_CODES.Q
 * */
fun isAndroidQuince() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)

/**
 * Android: [11]
 * @since Android Api: [30]
 * @see Build.VERSION_CODES.R
 * */
fun isAndroidRedVelvetCake() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)

/**
 * Android: [12]
 * @since Android Api: [31]
 * @see Build.VERSION_CODES.S
 * */
fun isAndroidSnowCone() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)

/**
 * Android: [12L]
 * @since Android Api: [32]
 * @see Build.VERSION_CODES.S_V2
 * */
fun isAndroidSnowCone2() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2)

/**
 * Android: [13]
 * @since Android Api: [33]
 * @see Build.VERSION_CODES.TIRAMISU
 * */
fun isAndroidTiramisu() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)

fun Context.isPermissionGranted(permission: String): Boolean {
    if (!isAndroidMarshmallow()) return true
    return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.isPermissionDenied(permission: String): Boolean {
    if (!isAndroidMarshmallow()) return false
    return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED
}

@RequiresApi(Build.VERSION_CODES.M)
fun Activity.requestPermissions(
    requestCode: Int, vararg permissions: String
) = this.requestPermissions(permissions, requestCode)

@RequiresPermission(Manifest.permission.VIBRATE)
fun Context.vibrateDevice(milliseconds: Long = 20) {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE)
            as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(
            VibrationEffect.createOneShot(
                milliseconds, VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(milliseconds)
    }
}

@SuppressLint("HardwareIds")
@RequiresPermission(Manifest.permission.READ_PHONE_STATE)
fun Context.getDeviceId(): String = Settings.Secure.getString(
    contentResolver, Settings.Secure.ANDROID_ID
)