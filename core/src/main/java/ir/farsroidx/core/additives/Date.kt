@file:Suppress("unused")

package ir.farsroidx.core.additives

import android.annotation.SuppressLint
import android.text.format.DateFormat
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import java.text.SimpleDateFormat
import java.util.Date

// TODO: Date ============================================================================= Date ===

fun timestamp() = System.currentTimeMillis()

/**
 * @see <a href="http://developer.android.com/reference/android/text/format/DateFormat.html">DateFormat</a>
 * */
fun getDateTime(
    format: CharSequence = "yyyy/MM/dd hh:mm:ss"
): CharSequence {
    return DateFormat.format(
        format, Date(
            timestamp()
        )
    )
}

/**
 * @see <a href="https://github.com/samanzamani/PersianDate#persiandateformat">PersianDate</a>
 * */
fun getPersianDateTime(
    pattern   : String = "Y/m/d H:i:s",
    raiseDay  : Int?   = null,
    raiseMonth: Int?   = null,
    raiseWeek : Int?   = null,
    raiseYear : Int?   = null
): CharSequence {
    return PersianDateFormat.format(
        PersianDate(
            timestamp()
        ).apply {
            raiseYear ?.let { addYear(it)  }
            raiseMonth?.let { addMonth(it) }
            raiseWeek ?.let { addWeek(it)  }
            raiseDay  ?.let { addDay(it)   }
        },
        pattern
    )
}

/**
 * @see <a href="https://github.com/samanzamani/PersianDate#persiandateformat">PersianDate</a>
 * */
@SuppressLint("SimpleDateFormat")
fun String?.toPersianDate(
    fromPattern: String = "yyyy-MM-dd'T'HH:mm:ss",
    toPattern  : String = "Y/m/d H:i:s",
    raiseDay   : Int?   = null,
    raiseMonth : Int?   = null,
    raiseWeek  : Int?   = null,
    raiseYear  : Int?   = null
): CharSequence {

    if (this == null) return ""

    return try {

        val parser = SimpleDateFormat(fromPattern)
        val date   = parser.parse(this)

        PersianDateFormat(toPattern)
            .format(
                PersianDate(
                    date
                ).apply {
                    raiseYear ?.let { addYear(it)  }
                    raiseMonth?.let { addMonth(it) }
                    raiseWeek ?.let { addWeek(it)  }
                    raiseDay  ?.let { addDay(it)   }
                }
            )

    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}


/**
 * @see <a href="https://github.com/samanzamani/PersianDate#persiandateformat">PersianDate</a>
 * */
fun Long?.toPersianDateTime(
    pattern   : String = "Y/m/d H:i:s",
    raiseDay  : Int?   = null,
    raiseMonth: Int?   = null,
    raiseWeek : Int?   = null,
    raiseYear : Int?   = null
): CharSequence = this.toPersianDate(
    pattern, raiseDay, raiseMonth, raiseWeek, raiseYear
)

/**
 * @see <a href="https://github.com/samanzamani/PersianDate#persiandateformat">PersianDate</a>
 * */
fun Long?.toPersianDate(
    pattern   : String = "Y/m/d",
    raiseDay  : Int?   = null,
    raiseMonth: Int?   = null,
    raiseWeek : Int?   = null,
    raiseYear : Int?   = null
): CharSequence {
    return if (this == null) ""
    else {
        PersianDateFormat(pattern).format(
            PersianDate(
                this
            ).apply {
                raiseYear ?.let { addYear(it)  }
                raiseMonth?.let { addMonth(it) }
                raiseWeek ?.let { addWeek(it)  }
                raiseDay  ?.let { addDay(it)   }
            }
        )
    }
}

/**
 * @see <a href="https://github.com/samanzamani/PersianDate#persiandateformat">PersianDate</a>
 * */
fun Long?.toPersianDate(pattern: String = "Y/m/d"): String {
    return if (this == null) ""
    else {
        PersianDateFormat(pattern).format(
            PersianDate(
                this
            )
        )
    }
}