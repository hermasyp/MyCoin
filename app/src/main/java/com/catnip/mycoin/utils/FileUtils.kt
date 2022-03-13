package com.catnip.mycoin.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.ParcelFileDescriptor
import java.io.File
import java.io.FileDescriptor
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object FileUtils {
    @Throws(IOException::class)
    fun getBitmapFromUri(contentResolver: ContentResolver, uri: Uri): Bitmap {
        val parcelFileDescriptor: ParcelFileDescriptor? =
            contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor: FileDescriptor? = parcelFileDescriptor?.fileDescriptor
        val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor?.close()
        return image
    }

    fun saveBitmapToLocalStorage(context: Context, bitmap: Bitmap) : File?{
        val file: File? = context.getExternalFilesDir("/cache-upload/${UUID.randomUUID()}.png")
        val path = File(file?.parent)
        try {
            // build directory
            if (file?.parent != null && !path.isDirectory) {
                path.mkdirs()
            }
            // output image to file
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos)
            fos.flush()
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }finally {
            return file
        }
    }
}