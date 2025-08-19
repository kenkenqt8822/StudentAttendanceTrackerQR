package com.example.attendance.data

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.telephony.SmsManager
import androidx.core.app.ActivityCompat
import java.text.SimpleDateFormat
import java.util.*

object SmsSender {
    fun formatMessage(template: String, name: String, teacher: TeacherPrefs): String {
        val now = Date()
        val time = SimpleDateFormat("h:mm a", Locale.getDefault()).format(now)
        return template
            .replace("{NAME}", name)
            .replace("{TIME}", time)
            .replace("{DISMISSAL}", teacher.dismissalTime)
            .replace("{TEACHER}", teacher.teacherName)
            .replace("{GRADE}", teacher.gradeSection)
            .replace("{SY}", teacher.schoolYear)
    }

    fun sendSms(activity: Activity, phone: String, message: String) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.SEND_SMS), 1001)
            return
        }
        val sms = SmsManager.getDefault()
        val parts = sms.divideMessage(message)
        sms.sendMultipartTextMessage(phone, null, parts, null, null)
    }
}
