package com.example.attendance.ui.students

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.attendance.R
import com.example.attendance.data.QRUtil
import com.example.attendance.data.db.AppDatabase
import kotlinx.coroutines.launch
import org.json.JSONObject

class StudentDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)

        val tvName: TextView = findViewById(R.id.tvName)
        val tvLRN: TextView = findViewById(R.id.tvLRN)
        val tvParent: TextView = findViewById(R.id.tvParent)
        val tvPhone: TextView = findViewById(R.id.tvPhone)
        val imgQR: ImageView = findViewById(R.id.imgQR)

        val id = intent.getLongExtra("studentId", -1L)
        lifecycleScope.launch {
            val student = AppDatabase.get(this@StudentDetailActivity).studentDao().getById(id) ?: return@launch
            tvName.text = student.name
            tvLRN.text = "LRN: ${student.lrn}"
            tvParent.text = "Parent: ${student.parentName}"
            tvPhone.text = "Phone: ${student.parentPhone}"
            val payload = JSONObject(mapOf("lrn" to student.lrn)).toString()
            val bmp: Bitmap = QRUtil().generateQR(payload, 512)
            imgQR.setImageBitmap(bmp)
        }
    }
}
