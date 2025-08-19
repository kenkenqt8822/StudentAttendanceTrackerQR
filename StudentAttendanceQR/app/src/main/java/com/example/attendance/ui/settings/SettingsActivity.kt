package com.example.attendance.ui.settings

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.attendance.R
import com.example.attendance.data.TeacherPrefs

class SettingsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val prefs = TeacherPrefs(this)

        val etName: EditText = findViewById(R.id.etTeacherName)
        val etGS: EditText = findViewById(R.id.etGradeSection)
        val etSY: EditText = findViewById(R.id.etSchoolYear)
        val etDismiss: EditText = findViewById(R.id.etDismissalTime)
        val etArr: EditText = findViewById(R.id.etArrivalTemplate)
        val etDis: EditText = findViewById(R.id.etDismissalTemplate)

        etName.setText(prefs.teacherName)
        etGS.setText(prefs.gradeSection)
        etSY.setText(prefs.schoolYear)
        etDismiss.setText(prefs.dismissalTime)
        etArr.setText(prefs.arrivalTemplate)
        etDis.setText(prefs.dismissalTemplate)

        findViewById<Button>(R.id.btnSaveSettings).setOnClickListener {
            prefs.teacherName = etName.text.toString()
            prefs.gradeSection = etGS.text.toString()
            prefs.schoolYear = etSY.text.toString()
            prefs.dismissalTime = etDismiss.text.toString()
            prefs.arrivalTemplate = etArr.text.toString()
            prefs.dismissalTemplate = etDis.text.toString()
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
