@file:Suppress("DEPRECATION", "unused")

package ir.farsroidx.core.exception

import android.app.ActivityManager
import android.app.Application
import android.app.ApplicationErrorReport
import android.content.Context
import android.content.Intent
import android.os.Debug
import kotlin.system.exitProcess

class ExceptionHandler(
    private val applicationBaseContext: Context, private val developerEmail: String
) : Thread.UncaughtExceptionHandler {

    companion object {

        const val EXTRA_THROWABLE = "extraThrowable"
        const val EXTRA_DEVELOPER = "extraDeveloper"

        fun install(context: Application, developerEmail: String = "") {
            if (!Debug.waitingForDebugger() && !Debug.isDebuggerConnected()) {
                Thread.setDefaultUncaughtExceptionHandler(
                    ExceptionHandler(context, developerEmail)
                )
            }
        }
    }

    private val defaultHandler  = Thread.getDefaultUncaughtExceptionHandler()
    private val activityManager = applicationBaseContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        try {
            applicationBaseContext.startActivity(
                Intent(
                    applicationBaseContext.applicationContext, ExceptionActivity::class.java
                ).setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                ).putExtra(
                    EXTRA_THROWABLE, getCrashDetail(throwable)
                ).putExtra(
                    EXTRA_DEVELOPER, developerEmail
                )
            )
        } catch (throwable: Throwable) {
            defaultHandler?.uncaughtException(thread, throwable)
        } finally {
            killApplication()
        }
    }

    private fun getCrashDetail(throwable: Throwable): ApplicationErrorReport {

        val pid = android.os.Process.myPid()

        var currentProcessName = ":${applicationBaseContext.packageName}"

        for (processInfo in activityManager.runningAppProcesses) {
            if (processInfo.pid == pid) {
                currentProcessName = processInfo.processName
                break
            }
        }

        return ApplicationErrorReport().apply {
            installerPackageName = applicationBaseContext.packageManager.getInstallerPackageName(
                applicationBaseContext.packageName
            )
            crashInfo            = ApplicationErrorReport.CrashInfo(throwable)
            packageName          = applicationBaseContext.packageName
            processName          = currentProcessName
            type                 = ApplicationErrorReport.TYPE_CRASH
            time                 = System.currentTimeMillis()
            systemApp            = false
        }
    }

    private fun killApplication() {
        android.os.Process.killProcess( android.os.Process.myPid() )
        exitProcess(-100)
    }
}
