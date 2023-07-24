@file:Suppress("DEPRECATION", "UNUSED_PARAMETER")

package ir.farsroidx.core.exception

import android.app.ActivityManager
import android.app.ApplicationErrorReport
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.Layout
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.AlignmentSpan
import android.view.View
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.inSpans
import androidx.core.text.scale
import androidx.core.text.toHtml
import androidx.core.text.underline
import androidx.lifecycle.lifecycleScope
import ir.farsroidx.core.CoreActivity
import ir.farsroidx.core.R
import ir.farsroidx.core.additives.gone
import ir.farsroidx.core.additives.visible
import ir.farsroidx.core.databinding.ActivityExceptionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExceptionActivity : CoreActivity<ActivityExceptionBinding>() {

    private var developerEmail: String = ""
    private val emailType     : String = "text/html"
    private val emailSubject  : String = "Crash Report"
    private var emailHtmlBody : String = ""

    override fun onInitialized() {

        binding.btnClose.setOnClickListener(::onClosePressed)

        binding.btnSendMail.setOnClickListener(::onSendPressed)

        developerEmail = intent.getStringExtra(ExceptionHandler.EXTRA_DEVELOPER) ?: ""

        if (developerEmail.isEmpty()) {
            binding.btnSendMail.gone()
        } else {
            binding.btnSendMail.visible()
        }

        intent.getParcelableExtra<ApplicationErrorReport>(ExceptionHandler.EXTRA_THROWABLE)
            ?.let { errorReport ->
                try {
                    if (developerEmail.isNotEmpty()) {
                        showErrorInfo(errorReport)
                    } else {
                        Toast.makeText(
                            this, "Developer Email not configuration.", Toast.LENGTH_LONG
                        ).show()
                        finish()
                    }
                } catch (throwable: Throwable) {
                    showReportActivity(throwable)
                }
            }
    }

    private fun onClosePressed(view: View) {
        finish()
    }

    private fun onSendPressed(view: View) {

        Toast.makeText(this, getString(R.string.please_wait), Toast.LENGTH_LONG).show()

        try {

            ShareCompat.IntentBuilder(this)
                .setType(emailType)
                .addEmailTo(developerEmail)
                .setSubject(emailSubject)
                .setHtmlText(emailHtmlBody)
                .startChooser()

            lifecycleScope.launch(Dispatchers.IO) {
                delay(1000)
                finish()
            }

        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.email_app_not_found), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun showErrorInfo(report: ApplicationErrorReport) {

        val html = StringBuilder()

        binding.crashTextView.apply {

            movementMethod = LinkMovementMethod.getInstance()

            val builder = buildSpannedString {

                inSpans(
                    AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER)
                ) {
                    color(Color.parseColor("#80000000")) {
                        bold {
                            appendByLine(
                                "[ " + report.crashInfo.exceptionClassName + " ]"
                            )
                        }
                    }
                }

                inSpans(
                    AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER)
                ) {
                    color(Color.parseColor("#F44336")) {
                        bold {
                            scale(1.1f) {
                                appendByLine(
                                    report.crashInfo.exceptionMessage, 2
                                )
                            }
                        }
                    }
                }

                inSpans(
                    AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER)
                ) {
                    scale(0.9f) {
                        color(Color.parseColor("#3399ff")) {
                            bold {
                                appendByLine(
                                    report.crashInfo.throwClassName.trim()
                                )
                            }
                        }
                    }
                }

                inSpans(
                    AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER)
                ) {
                    color(Color.parseColor("#FF1A6A")) {
                        bold {
                            scale(1.2f) {
                                append(report.crashInfo.throwFileName + " : ")
                            }
                            scale(2f) {
                                underline {
                                    append(report.crashInfo.throwLineNumber.toString())
                                }
                            }
                        }
                    }
                }

//                scale(0.5f) {
//                    appendByLine("Text at half size")
//                }
//
//                backgroundColor(Color.LTGRAY) {
//                    appendByLine("Background LightGRAY")
//                }
//
//                bold {
//                    underline {
//                        appendByLine("bold and underlined")
//                    }
//                }
//
//                italic {
//                    underline {
//                        appendByLine("italic and underlined")
//                    }
//                }
//
//                bold {
//                    italic {
//                        appendByLine("bold and italic")
//                    }
//                }
//
//                strikeThrough {
//                    append("strikeThrough")
//                }
//
//                superscript {
//                    appendByLine("2f")
//                }
//
//                inSpans(
//                    object : ClickableSpan() {
//                        override fun onClick(view: View) {
//                            Toast.makeText(baseContext, "test", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                ) {
//                    color(Color.BLUE) {
//                        bold {
//                            appendByLine("@MinooGroup")
//                        }
//                    }
//                }
            }

            text = builder

            html.append(builder.toHtml())
        }

        html.append("<h2>" + getString(R.string.stack_trace) + "</h2>")

        binding.traceTextView.apply {

            val spannableString = SpannableString(report.crashInfo.stackTrace.trim())

//            try {
//
//                val startClass = stackTrace.indexOf(stackClass)
//                val endClass   = startClass + stackClass.length
//
//                spannableString.setSpan(
//                    ForegroundColorSpan(Color.BLACK),
//                    startClass, endClass,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//
//                spannableString.setSpan(
//                    StyleSpan(Typeface.BOLD),
//                    startClass, endClass,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//
//                // =================================================================================
//
//                val startMessage = stackTrace.indexOf(stackMessage)
//                val endMessage   = startMessage + stackMessage.length
//
//                spannableString.setSpan(
//                    ForegroundColorSpan(Color.parseColor("#F44336")),
//                    startMessage, endMessage,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//
//                spannableString.setSpan(
//                    StyleSpan(Typeface.BOLD),
//                    startMessage, endMessage,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//
//                // =================================================================================
//
//                val startLocation = stackTrace.indexOf(stackLocation)
//                val endLocation   = startLocation + stackLocation.length
//
//                spannableString.setSpan(
//                    ForegroundColorSpan(Color.BLUE),
//                    startLocation, endLocation,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//
//                spannableString.setSpan(
//                    StyleSpan(Typeface.BOLD),
//                    startLocation, endLocation,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//
//            } catch (e: IndexOutOfBoundsException) {
//                e.printStackTrace()
//            }

            text = spannableString

            html.append(spannableString.toHtml())
        }

        emailHtmlBody = html.toString()
    }

    private fun showReportActivity(throwable: Throwable) {

        val pid = android.os.Process.myPid()

        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        var currentProcessName = ":${packageName}"

        for (processInfo in manager.runningAppProcesses) {
            if (processInfo.pid == pid) {
                currentProcessName = processInfo.processName
                break
            }
        }

        ApplicationErrorReport().apply {
            installerPackageName = packageManager.getInstallerPackageName(packageName)
            crashInfo            = ApplicationErrorReport.CrashInfo(throwable)
            packageName          = this@ExceptionActivity.packageName
            processName          = currentProcessName
            type                 = ApplicationErrorReport.TYPE_CRASH
            time                 = System.currentTimeMillis()
            systemApp            = false
            startActivity(
                Intent(Intent.ACTION_APP_ERROR)
                    .setPackage("com.google.android.feedback")
                    .putExtra(Intent.EXTRA_BUG_REPORT, this)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    private fun SpannableStringBuilder.appendByLine(
         value: CharSequence, enterCount: Int = 1
    ): SpannableStringBuilder {
        this.append(value)
        for (i in 0 until enterCount) {
            this.append("\n")
        }
        return this
    }
}