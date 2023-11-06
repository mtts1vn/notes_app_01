package com.appxstudios.fnag.functions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import java.net.HttpURLConnection
import java.net.URL
import android.os.Handler
import android.util.LruCache
import androidx.core.graphics.get
import java.util.concurrent.Executors


object ImageDownloader  {
    private lateinit var memoryCache: LruCache<String, Bitmap>;

    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 7;
        memoryCache = object: LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String?, value: Bitmap): Int {
                return value.byteCount / 1024;
            }
        }
    }

    fun download(imageView: ImageView, url: String, handler: Handler) {
        val image: Bitmap? = getBitmapFromMemory(url);
        if (image != null) {
            handler.post { imageView.setImageBitmap(image) }
        } else {
            val executor = Executors.newSingleThreadExecutor();
            executor.execute {
                val conn = URL(url).openConnection() as HttpURLConnection;
                try {
                    val statusCode: Int = conn.responseCode.toInt();
                    if (statusCode == 200) {
                        val inputStream = conn.inputStream;
                        println(inputStream)
                        val bitmap: Bitmap = resizeBitmap(BitmapFactory.decodeStream(inputStream), 400,400);
                        memoryCache.put(url, bitmap);
                        handler.post { imageView.setImageBitmap(bitmap); }
                    }
                } catch (e: Exception) {
                    println("ERRO: $e");
                }
            }
        }
    }

    fun removeBitMapFromCache(imageKey: String) {
        memoryCache.remove(imageKey);
    }

    private fun getBitmapFromMemory (imageKey: String?): Bitmap? {
        return memoryCache[imageKey];
    }

    fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        if (width <= maxWidth && height <= maxHeight) {
            return bitmap
        }

        val scaleWidth = maxWidth.toFloat() / width
        val scaleHeight = maxHeight.toFloat() / height
        val scaleFactor = scaleWidth.coerceAtMost(scaleHeight)
        val newWidth = (width * scaleFactor).toInt()
        val newHeight = (height * scaleFactor).toInt()

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }
}
