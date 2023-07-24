@file:Suppress("unused")

package ir.farsroidx.core.additives

import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

// TODO: Date ============================================================================= Date ===

fun getUnixTimeStamp() = System.currentTimeMillis()

fun getPersianDateTime(
    format: String = "Y-m-d H:i:s",
    raiseDay: Int? = null
): String {
    return PersianDateFormat(format).format(
        PersianDate().apply {
            if (raiseDay != null) {
                addDay(raiseDay)
            }
        }
    )
}