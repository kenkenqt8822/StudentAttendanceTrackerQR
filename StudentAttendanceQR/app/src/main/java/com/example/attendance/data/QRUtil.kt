package com.example.attendance.data

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

class QRUtil {
    fun generateQR(content: String, size: Int = 512): Bitmap {
        val hints = mapOf(EncodeHintType.MARGIN to 1)
        val result: BitMatrix = MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints)
        val w = result.width
        val h = result.height
        val pixels = IntArray(w * h)
        for (y in 0 until h) {
            val offset = y * w
            for (x in 0 until w) {
                pixels[offset + x] = if (result.get(x, y)) 0xFF000000.toInt() else 0xFFFFFFFF.toInt()
            }
        }
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h)
        return bitmap
    }
}
