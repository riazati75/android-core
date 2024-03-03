package ir.farsroidx.core.additives

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment

fun Fragment.openInAppBrowser(webLink: String) = requireContext().openInAppBrowser(webLink)

fun Context.openInAppBrowser(webLink: String) {

    val customTabsIntent = CustomTabsIntent.Builder()
        // set the default color scheme
//        .setDefaultColorSchemeParams(
//            CustomTabColorSchemeParams.Builder()
//                .setToolbarColor(colorPrimaryLight)
//                .build()
//        )
        // set the alternative dark color scheme
//        .setColorSchemeParams(
//            CustomTabsIntent.COLOR_SCHEME_DARK, CustomTabColorSchemeParams.Builder()
//                .setToolbarColor(colorPrimaryDark)
//                .build()
//        )
        .setStartAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out)
        .setExitAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out)
        .setShareState(CustomTabsIntent.SHARE_STATE_OFF)
//        .setUrlBarHidingEnabled(true)
        .setShowTitle(true)
//        .setCloseButtonIcon(
//            toBitmap(myCustomCloseIcon)
//        )
        .build()

    customTabsIntent.intent.putExtra(
        Intent.EXTRA_REFERRER,
        Uri.parse("android-app://$packageName")
    )

    customTabsIntent.launchUrl(this, Uri.parse(webLink))
}