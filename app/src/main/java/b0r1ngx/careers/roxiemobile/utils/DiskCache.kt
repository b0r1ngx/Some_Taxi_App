package b0r1ngx.careers.roxiemobile.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import b0r1ngx.careers.roxiemobile.api.TaxiService
import java.io.*
import java.security.MessageDigest
import java.util.*

/*
 *
 * adopted from:
 * Source (c) https://habr.com/ru/post/133239/
 */
class DiskCache {
    fun getBitmap(externalCacheDir: File, fileCacheLifeTime: Int, fileName: String): Bitmap? {
        Log.d("Test", "getBitmap() START")
        var bitmap: Bitmap? = null

        if (fileCacheLifeTime != 0) {
            val file = File(externalCacheDir, fileName)
            val nowTime = Date().time / 1000
            val fileLastTimeModified = file.lastModified() / 1000

            try {
                if (file.exists()) {
                    if (fileLastTimeModified + fileCacheLifeTime < nowTime) {
                        file.delete()
                        downloadBitmapAndSaveToFile(file, fileName)
                    }
                } else {
                    downloadBitmapAndSaveToFile(file, fileName)
                }

                bitmap = BitmapFactory.decodeStream(FileInputStream(file))
            } catch (e: Exception) {
                Log.d("Test", "downloadImage(), in if branch ${e.printStackTrace()}")
                e.printStackTrace()
            }

            if (bitmap == null) file.delete()
        } else {
            try {
                bitmap = TaxiService.getPhotoBitmap(fileName)
            } catch (e: Exception) {
                Log.d("Test", "downloadImage(), global else branch ${e.printStackTrace()}")
            }
        }

        Log.d("Test", "getBitmap() END")
        return bitmap
    }

    private fun downloadBitmapAndSaveToFile(file: File, fileName: String) {
        Log.d("Test", "downloadBitmapAndSaveToFile() START")
        val bitmapFromInternet = TaxiService.getPhotoBitmap(fileName)
        try {
            FileOutputStream(file).use {
                bitmapFromInternet.compress(Bitmap.CompressFormat.JPEG, 100, it)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.d("Test", "downloadBitmapAndSaveToFile() END")
    }
}