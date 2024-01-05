@file:Suppress("unused")

package ir.farsroidx.core.additives

import android.telephony.PhoneNumberUtils
import android.widget.EditText

// TODO: Validation ================================================================= Validation ===

fun String.isValidIranianNationalCode() = this.takeIf {
    it.length == 10
}?.mapNotNull(Char::digitToIntOrNull)?.takeIf { it.size == 10 }?.let {
    val check = it[9]
    val sum = it.slice(0..8).mapIndexed { i, x -> x * (10 - i) }.sum() % 11
    if (sum < 2) check == sum else check + sum == 11
} ?: false

fun EditText.isValidPhone(): Boolean {
    return readString().startsWith("09") &&
        PhoneNumberUtils.isGlobalPhoneNumber(readString())
}