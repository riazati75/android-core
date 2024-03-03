package ir.farsroidx.core.abstracts

import android.text.Editable
import android.text.TextWatcher

abstract class AbstractTextWatcher : TextWatcher {
    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(editable: Editable) {}
}