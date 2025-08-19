package com.example.attendance.ui.scan

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.attendance.R
import com.example.attendance.data.SmsSender
import com.example.attendance.data.TeacherPrefs
import com.example.attendance.data.db.AppDatabase
import com.example.attendance.data.model.AttendanceLog
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.launch
import org.json.JSONObject

class ScannerActivity: AppCompatActivity() {
    private lateinit var mode: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        mode = intent.getStringExtra("mode") ?: "ARRIVAL"
        findViewById<TextView>(R.id.tvMode).text = "Scanner Mode: $mode"

        val integrator = IntentIntegrator(this)
        integrator.setOrientationLocked(false)
        integrator.setBeepEnabled(true)
        integrator.setPrompt("Scan student QR")
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: android.content.Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                finish()
            } else {
                handleScan(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleScan(contents: String) {
        val prefs = TeacherPrefs(this)
        try {
            val json = JSONObject(contents)
            val lrn = json.getString("lrn")
            lifecycleScope.launch {
                val db = AppDatabase.get(this@ScannerActivity)
                val student = db.studentDao().getByLRN(lrn)
                if (student == null) {
                    Toast.makeText(this@ScannerActivity, "Unknown LRN", Toast.LENGTH_LONG).show()
                    finish(); return@launch
                }
                // log attendance
                db.attendanceDao().insert(AttendanceLog(
                    studentId = student.id,
                    timestamp = System.currentTimeMillis(),
                    type = mode
                ))
                // send SMS
                val template = if (mode == "ARRIVAL") prefs.arrivalTemplate else prefs.dismissalTemplate
                val msg = SmsSender.formatMessage(template, student.name, prefs)
                SmsSender.sendSms(this@ScannerActivity, student.parentPhone, msg)
                Toast.makeText(this@ScannerActivity, "Logged & SMS sent to ${student.parentPhone}", Toast.LENGTH_LONG).show()
                finish()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Invalid QR content", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
