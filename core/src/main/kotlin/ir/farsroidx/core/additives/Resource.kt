@file:Suppress("unused")

package ir.farsroidx.core.additives

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.annotation.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import org.xmlpull.v1.XmlPullParser
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

// TODO: Resource ===================================================================== Resource ===

fun View.getStringResource(@StringRes resId: Int) = context.getStringResource(resId)

fun Fragment.getStringResource(@StringRes resId: Int) = requireContext().getStringResource(resId)

fun Context.getStringResource(@StringRes resId: Int) = getString(resId)

fun View.getIntArrayResource(@ArrayRes resId: Int) = context.getIntArrayResource(resId)

fun Fragment.getIntArrayResource(@ArrayRes resId: Int) = requireContext().getIntArrayResource(resId)

fun Context.getIntArrayResource(@ArrayRes resId: Int) = resources.getIntArray(resId)

fun View.getStringArrayResource(@ArrayRes resId: Int): Array<String> = context.getStringArrayResource(resId)

fun Fragment.getStringArrayResource(@ArrayRes resId: Int): Array<String> = requireContext().getStringArrayResource(resId)

fun Context.getStringArrayResource(@ArrayRes resId: Int): Array<String> = resources.getStringArray(resId)

fun View.getTextArrayResource(@ArrayRes resId: Int): Array<CharSequence> = context.getTextArrayResource(resId)

fun Fragment.getTextArrayResource(@ArrayRes resId: Int): Array<CharSequence> = requireContext().getTextArrayResource(resId)

fun Context.getTextArrayResource(@ArrayRes resId: Int): Array<CharSequence> = resources.getTextArray(resId)

fun View.getDimensionResource(@DimenRes resId: Int) = context.getDimensionResource(resId)

fun Fragment.getDimensionResource(@DimenRes resId: Int) = requireContext().getDimensionResource(resId)

fun Context.getDimensionResource(@DimenRes resId: Int) = resources.getDimension(resId)

fun View.getColorResource(@ColorRes resId: Int) = context.getColorResource(resId)

fun Fragment.getColorResource(@ColorRes resId: Int) = requireContext().getColorResource(resId)

fun Context.getColorResource(@ColorRes resId: Int) = ResourcesCompat.getColor(resources, resId, null)

fun View.getTypeFace(@FontRes resId: Int) = context.getTypeFace(resId)

fun Fragment.getTypeFace(@FontRes resId: Int) = requireContext().getTypeFace(resId)

fun Context.getTypeFace(@FontRes resId: Int) = ResourcesCompat.getFont(this, resId)

fun View.getThemeColor(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
) = context.getThemeColor(
    attrColor, typedValue, resolveRefs
)

fun Fragment.getThemeColor(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
) = requireContext().getThemeColor(
    attrColor, typedValue, resolveRefs
)

@ColorInt
fun Context.getThemeColor(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

fun View.getTextFromAssets(
    path: String,
    exception: (e: Exception) -> Unit = {}
) = context.getTextFromAssets(path, exception)

fun Fragment.getTextFromAssets(
    path: String,
    exception: (e: Exception) -> Unit = {}
) = requireContext().getTextFromAssets(path, exception)

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

fun View.loadPropertiesResource(name: String) = context.loadPropertiesResource(name)

fun Fragment.loadPropertiesResource(name: String) = requireContext().loadPropertiesResource(name)

fun Context.loadPropertiesResource(name: String): Properties {
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

fun View.loadXmlResource(@XmlRes resId: Int) = context.loadXmlResource(resId)

fun Fragment.loadXmlResource(@XmlRes resId: Int) = requireContext().loadXmlResource(resId)

fun Context.loadXmlResource(@XmlRes resId: Int): XmlPullParser {
    return resources.getXml(resId)
}
