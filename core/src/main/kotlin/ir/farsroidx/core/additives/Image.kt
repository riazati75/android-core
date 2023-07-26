@file:Suppress("unused", "DEPRECATION")

package ir.farsroidx.core.additives

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.target.ViewTarget
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.UUID

// TODO: Image =========================================================================== Image ===

fun ImageView.loadPhotoFromUrl(
    imageUrl: String,
    @DrawableRes placeHolder: Int,
    @DrawableRes errorPlaceError: Int = placeHolder,
    builderBlock: (RequestBuilder<Drawable>) -> Unit = {},
    targetBlock: (ViewTarget<ImageView, Drawable>) -> Unit = {},
) {
    Glide.with(context)
        .load(imageUrl)
        .centerCrop()
        .placeholder(placeHolder)
        .error(errorPlaceError)
        .apply {
            builderBlock(this)
        }
        .into(this@loadPhotoFromUrl)
        .apply {
            targetBlock(this)
        }
}

fun ImageView.loadPhotoFromUrl(
    context: Context,
    imageUrl: Uri,
    @DrawableRes placeHolder: Int,
    @DrawableRes errorPlaceError: Int = placeHolder,
    errorBlock: (GlideException?) -> Unit = {},
    successBlock: (String) -> Unit = {},
) {
    Glide.with(context).load(imageUrl).centerCrop()
        .placeholder(placeHolder).error(errorPlaceError)
        .listener(object : RequestListener<Drawable> {

            override fun onLoadFailed(
                exception: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                errorBlock(exception)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {

                val bitmap: Bitmap = resource?.toBitmap() as Bitmap

                val mImagePath = context.saveImageToInternalStorage(bitmap)

                successBlock(mImagePath)

                return false
            }
        })
        .into(this)
}

fun Context.saveImageToInternalStorage(bitmap: Bitmap): String {

    val wrapper = ContextWrapper(applicationContext)

    var file = wrapper.getDir("GearsRunImages", Context.MODE_PRIVATE)

    file = File(file,"${UUID.randomUUID()}.jpg")

    try {

        val stream : OutputStream = FileOutputStream(file)

        // iLog("Width : " + bitmap.width)
        // iLog("Height: " + bitmap.height)

        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)

        stream.flush()

        stream.close()

    } catch (e: IOException){
        e.printStackTrace()
    }

    return file.absolutePath
}