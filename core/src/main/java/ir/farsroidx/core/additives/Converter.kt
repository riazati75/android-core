@file:Suppress("unused")

package ir.farsroidx.core.additives

import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Base64
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DecimalFormat
import java.util.Locale

// TODO: Converter =================================================================== Converter ===

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.px: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Float.dp: Float
    get() = (this / Resources.getSystem().displayMetrics.density)

val String.color: Int
    get() = Color.parseColor(this)

val String.hexToColor: Int
    get() =
        if (this.startsWith("#")) {
            Color.parseColor(this)
        } else {
            Color.parseColor("#$this")
        }

val String.md5: String
    get() {

        try {

            val digest = MessageDigest.getInstance("MD5")

            digest.update(this.toByteArray())

            val messageDigest = digest.digest()

            val hexString = StringBuilder()

            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2) h = "0$h"
                hexString.append(h)
            }

            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }

fun String.htmlToSpannable(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }
}

fun Spanned.htmlFromString(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.toHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.toHtml(this)
    }
}

fun Number?.toRialFormat(): String {
    return DecimalFormat("###,###,###")
        .format(
            this ?: 0
        )
}

fun String?.toRialFormat(): String {
    return DecimalFormat("###,###,###")
        .format(
            this ?: 0
        )
}

fun String.toBase64(): String {
    return Base64.encodeToString(
        this.toByteArray(
            charset("UTF-8")
        ),
        Base64.DEFAULT
    )
}

fun String.fromBase64(): String {
    return Base64.decode(this, Base64.DEFAULT)
        .toString(
            charset("UTF-8")
        )
}

fun String.subString(startPosition: Int, readRow: Int = 1): String {
    return this.substring((startPosition - 1) , (startPosition - 1) + readRow)
}

fun String.subStringAsInt(startPosition: Int, readRow: Int = 1): Int {
    return this.substring((startPosition - 1) , (startPosition - 1) + readRow).toInt()
}

private val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()
fun String.camelToSnakeCase(): String {
    return camelRegex.replace(this) {
        "_${it.value}"
    }.lowercase()
}

private val snakeRegex = "_[a-zA-Z]".toRegex()
fun String.snakeToLowerCamelCase(): String {
    return snakeRegex.replace(this) {
        it.value.replace("_","")
            .uppercase()
    }
}

fun String.snakeToUpperCamelCase(): String {
    return this.snakeToLowerCamelCase()
        .replaceFirstChar {
            if (it.isLowerCase())
                it.titlecase(Locale.ROOT)
            else
                it.toString()
        }
}