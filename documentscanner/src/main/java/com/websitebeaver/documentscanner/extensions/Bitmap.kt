package com.websitebeaver.documentscanner.extensions

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import kotlin.math.sqrt

/**
 * This converts the bitmap to base64
 *
 * @return image as a base64 string
 */
fun Bitmap.toBase64(quality: Int): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
    return Base64.encodeToString(
        byteArrayOutputStream.toByteArray(),
        Base64.DEFAULT
    )
}

/**
 * This converts the bitmap to base64
 *
 * @param file the bitmap gets saved to this file
 */
fun Bitmap.saveToFile(context: Context, uri: Uri, quality: Int) {
    val outputStream = context.contentResolver.openOutputStream(uri)
    this.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
    outputStream?.close()
}


/**
 * This resizes the image, so that the byte count is a little less than targetBytes
 *
 * @param targetBytes the returned bitmap has a size a little less than targetBytes
 */
fun Bitmap.changeByteCountByResizing(targetBytes: Int): Bitmap {
    val scale = sqrt(targetBytes.toDouble() / byteCount.toDouble())
    return Bitmap.createScaledBitmap(
        this,
        (width * scale).toInt(),
        (height * scale).toInt(),
        true
    )
}