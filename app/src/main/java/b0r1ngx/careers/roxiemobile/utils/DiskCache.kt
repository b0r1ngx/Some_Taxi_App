package b0r1ngx.careers.roxiemobile.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import b0r1ngx.careers.roxiemobile.api.TaxiService
import java.io.*
import java.security.MessageDigest
import java.util.*

/*
 * Self Cache realisation and interaction to saving images,
 * adopted from:
 * Source (c) https://habr.com/ru/post/133239/
 */
class DiskCache {
    fun getBitmap(externalCacheDir: File, fileCacheLifeTime: Int, fileName: String): Bitmap? {
        Log.d("Test", "downloadImage() START")
        var bitmap: Bitmap? = null

        if (fileCacheLifeTime != 0) {
            val file = File(externalCacheDir, fileName)
            val nowTime = Date().time / 1000
            val fileLastTimeModified = file.lastModified() / 1000

            try {
                if (file.exists()) {
                    if (fileLastTimeModified + fileCacheLifeTime < nowTime) {
                        file.delete()
                        saveBitmapToFile(file, fileName)
                    }
                } else {
                    saveBitmapToFile(file, fileName)
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

        Log.d("Test", "downloadImage() END")
        return bitmap
    }

    private fun saveBitmapToFile(file: File, fileName: String) {
        val bitmap = TaxiService.getPhotoBitmap(fileName)
        try {
            FileOutputStream(file).use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun md5(s: String): String {
        try {
            val digest: MessageDigest = MessageDigest.getInstance("MD5")
            digest.update(s.toByteArray())
            val messageDigest: ByteArray = digest.digest()
            val hexString = StringBuffer()
            for (i in messageDigest.indices) {
                hexString.append(Integer.toHexString(0xFF and messageDigest[i].toInt()))
            }
            return hexString.toString()
        } catch (e: Exception) {
            Log.d("Test", "md5(), ${e.printStackTrace()}")
        }
        return ""
    }

    private fun fileSave(file: File, inputStream: InputStream, outputStream: FileOutputStream) {
        file.createNewFile()

        var i: Int
        try {
            while (inputStream.read().also { i = it } != -1) {
                outputStream.write(i)
            }
            outputStream.flush()
            outputStream.close()
        } catch (e: IOException) {
            Log.d("Test", "fileSave() ${e.printStackTrace()}")
        }
    }
}