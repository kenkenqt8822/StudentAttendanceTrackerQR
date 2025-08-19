package com.example.attendance.ui.students

import android.graphics.Bitmap
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.attendance.R
import com.example.attendance.data.QRUtil
import com.example.attendance.data.db.AppDatabase
import kotlinx.coroutines.launch
import org.json.JSONObject

class QRGridActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrgrid)
        val container: LinearLayout = findViewById(R.id.container)

        lifecycleScope.launch {
            val students = AppDatabase.get(this@QRGridActivity).studentDao().getAll()
            val qr = QRUtil()
            for (s in students) {
                val payload = JSONObject(mapOf("lrn" to s.lrn)).toString()
                val bmp: Bitmap = qr.generateQR(payload, 600)
                val title = TextView(this@QRGridActivity).apply {
                    text = "${s.name} (LRN: ${s.lrn})"
                    textSize = 18f
                    setPadding(0, 24, 0, 8)
                }
                val img = ImageView(this@QRGridActivity).apply {
                    setImageBitmap(bmp)
                    layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                }
                container.addView(title)
                container.addView(img)
            }
        }
    }
}
