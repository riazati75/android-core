@file:Suppress("unused")

package ir.farsroidx.core.additives

import android.annotation.SuppressLint
import android.text.format.DateFormat
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import java.text.SimpleDateFormat
import java.util.Date

// TODO: Date ============================================================================= Date ===

@SuppressLint("SimpleDateFormat")
private val mSimpleDateFormat  = SimpleDateFormat()
private val mPersianDateFormat = PersianDateFormat()

fun getUnixTimeStamp() = System.currentTimeMillis()

// https://github.com/samanzamani/PersianDate#persiandateformat
fun getPersianDateTime(
    format    : String = "Y-m-d H:i:s",
    raiseDay  : Int?   = null,
    raiseMonth: Int?   = null,
    raiseWeek : Int?   = null,
    raiseYear : Int?   = null
): CharSequence {
    return PersianDateFormat.format(
        PersianDate().apply {
            raiseYear?.let { addYear(it) }
            raiseMonth?.let { addMonth(it) }
            raiseWeek?.let { addWeek(it) }
            raiseDay?.let { addDay(it) }
        },
        format
    )
}

// http://developer.android.com/reference/android/text/format/DateFormat.html
fun getDateTime(
    format: CharSequence = "yyyy-MM-dd hh:mm:ss"
): CharSequence {
    return DateFormat.format(
        format, Date(
            getUnixTimeStamp()
        )
    )
}