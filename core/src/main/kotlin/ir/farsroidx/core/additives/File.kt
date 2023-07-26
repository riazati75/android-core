package ir.fanap.manaman.core.additives

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

enum class FileDirSaveStrategy { FILE, CACHE }

sealed class ImageSaveStrategy(val imageQuality: Int) {
    class JPG(imageQuality: Int) : ImageSaveStrategy(imageQuality)
    class PNG(imageQuality: Int) : ImageSaveStrategy(imageQuality)
}

fun Fragment.grantUriPermission(uri: Uri) = requireContext().grantUriPermission(uri)

fun Context.grantUriPermission(uri: Uri) {
    grantUriPermission(
        packageName, uri,
        Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
    )
}

fun Fragment.revokeUriPermission(uri: Uri) = requireContext().revokeUriPermission(uri)

fun Context.revokeUriPermission(uri: Uri) {
    revokeUriPermission(
        uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
    )
}

fun Fragment.saveFileToCacheDir(
    applicationId: String,
    fileName: String,
    saveStrategy: FileDirSaveStrategy,
    onFileCreated: (File) -> Unit = {}
): Uri = requireContext().saveFileToCacheDir(
    applicationId,
    fileName,
    saveStrategy,
    onFileCreated
)

fun Context.saveFileToCacheDir(
    applicationId: String,
    fileName: String,
    saveStrategy: FileDirSaveStrategy,
    onFileCreated: (File) -> Unit = {}
): Uri {

    val fileDirectory = when(saveStrategy) {
        FileDirSaveStrategy.FILE  -> filesDir
        FileDirSaveStrategy.CACHE -> cacheDir
    }

    val file = File(fileDirectory, fileName).apply {
        onFileCreated(this)
    }

    return FileProvider.getUriForFile(this, "$applicationId.provider", file)
}

fun Fragment.openFileAsPdf(uri: Uri, onException: (Exception) -> Unit = {}) =
    requireContext().openFileAsPdf(uri, onException)

fun Context.openFileAsPdf(uri: Uri, onException: (Exception) -> Unit = {}) {
    try {
        startActivity(
            Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
//                setDataAndType(uri, "image/*")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
        )
    } catch (exception: Exception) {
        onException(exception)
    }
}

fun Fragment.saveToStorage(
    bitmap: Bitmap,
    fileName: String = randomFileName("jpg"),
    filePostFix: String? = null,
    saveStrategy: ImageSaveStrategy = ImageSaveStrategy.JPG(100)
): String = requireContext().saveToStorage(
    bitmap,
    fileName,
    filePostFix,
    saveStrategy
)

fun Context.saveToStorage(
    bitmap: Bitmap,
    fileName: String = randomFileName("jpg"),
    filePostFix: String? = null,
    saveStrategy: ImageSaveStrategy = ImageSaveStrategy.JPG(100)
): String {

    var localFileName = fileName

    if (filePostFix != null) { localFileName += ".$filePostFix" }

    val file = File(getDir("Images", Context.MODE_PRIVATE), localFileName)

    try {

        FileOutputStream(file).apply {

            val compressFormat = when (saveStrategy) {
                is ImageSaveStrategy.JPG -> Bitmap.CompressFormat.JPEG
                is ImageSaveStrategy.PNG -> Bitmap.CompressFormat.PNG
            }

            bitmap.compress(
                compressFormat,
                saveStrategy.imageQuality,
                this
            )

            flush()

            close()
        }

    } catch (e: IOException) {
        e.printStackTrace()
    }

    return file.absolutePath
}

private fun randomFileName(postfix: String): String {
    return "${UUID.randomUUID()}.$postfix"
}