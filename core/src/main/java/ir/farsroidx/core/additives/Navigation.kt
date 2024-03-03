package ir.farsroidx.core.additives

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import ir.farsroidx.core.AbstractActivity
import ir.farsroidx.core.AbstractFragment

// TODO: Navigation ================================================================= Navigation ===

fun AbstractFragment<*,*, *>.navigateUp(requestCode: Int, bundle: Bundle) {

    if (activity != null && activity is AbstractActivity<*,*, *>) {

        (activity as AbstractActivity<*,*, *>).setBundle(requestCode, bundle)

    } else throw IllegalStateException(
        "You need to extend your activity from CoreActivity<*>"
    )

    findNavController().navigateUp()
}