@file:Suppress("unused")

package ir.farsroidx.core.additives

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import org.xmlpull.v1.XmlPullParser
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

// TODO: Resource ===================================================================== Resource ===

fun Fragment.getStringResource(@StringRes resId: Int) = requireContext().getStringResource(resId)

fun Context.getStringResource(@StringRes resId: Int) = getString(resId)

fun Fragment.getIntArrayResource(@ArrayRes resId: Int) = requireContext().getIntArrayResource(resId)

fun Context.getIntArrayResource(@ArrayRes resId: Int) = resources.getIntArray(resId)

fun Fragment.getStringArrayResource(@ArrayRes resId: Int): Array<String> = requireContext().getStringArrayResource(resId)

fun Context.getStringArrayResource(@ArrayRes resId: Int): Array<String> = resources.getStringArray(resId)

fun Fragment.getTextArrayResource(@ArrayRes resId: Int): Array<CharSequence> = requireContext().getTextArrayResource(resId)

fun Context.getTextArrayResource(@ArrayRes resId: Int): Array<CharSequence> = resources.getTextArray(resId)

fun Fragment.getDimensionResource(@DimenRes resId: Int) = requireContext().getDimensionResource(resId)

fun Context.getDimensionResource(@DimenRes resId: Int) = resources.getDimension(resId)

fun Fragment.getColorResource(@ColorRes resId: Int) = requireContext().getColorResource(resId)

fun Context.getColorResource(@ColorRes resId: Int) = ResourcesCompat.getColor(resources, resId, null)

fun Fragment.getTypeFace(@FontRes resId: Int) = requireContext().getTypeFace(resId)

fun Context.getTypeFace(@FontRes resId: Int) = ResourcesCompat.getFont(this, resId)

fun Context.getTextFromAssets(
    path: String,
    exception: (e: Exception) -> Unit = {}
): String {
    val stringBuilder = StringBuilder()
    var reader: BufferedReader? = null
    try {
        reader = BufferedReader(
            InputStreamReader(
                this.assets.open(path),
                "UTF-8"
            )
        )
        reader.forEachLine {
            stringBuilder.append(it)
            stringBuilder.append('\n')
        }
    } catch (e: IOException) {
        e.printStackTrace()
        exception(e)
    } finally {
        if (reader != null) {
            try {
                reader.close()
            } catch (e: IOException) {
                e.printStackTrace()
                exception(e)
            }
        }
    }
    return stringBuilder.toString().trim()
}

fun Context.loadFileProperties(name: String): Properties {
    @SuppressLint("DiscouragedApi")
    val properties = Properties().apply {
        load(
            resources.openRawResource(
                resources.getIdentifier(
                    name, "raw", packageName
                )
            )
        )
    }
    return properties
}

fun Context.loadFileXML(@XmlRes resId: Int): XmlPullParser {
    return resources.getXml(resId)
}
