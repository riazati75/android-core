package ir.farsroidx.core.additives

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import ir.farsroidx.core.CoreActivity
import ir.farsroidx.core.CoreFragment

// TODO: Navigation ================================================================= Navigation ===

fun CoreFragment<*>.navigateUp(requestCode: Int, bundle: Bundle) {

    if (activity != null && activity is CoreActivity<*>) {

        (activity as CoreActivity<*>).setBundle(requestCode, bundle)

    } else throw IllegalStateException(
        "You need to extend your activity from CoreActivity<*>"
    )

    findNavController().navigateUp()
}