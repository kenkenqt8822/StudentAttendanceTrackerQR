package com.example.attendance.data

import android.content.Context

class TeacherPrefs(context: Context) {
    private val sp = context.getSharedPreferences("teacher_prefs", Context.MODE_PRIVATE)

    var teacherName: String
        get() = sp.getString("teacherName", "") ?: ""
        set(v) { sp.edit().putString("teacherName", v).apply() }

    var gradeSection: String
        get() = sp.getString("gradeSection", "") ?: ""
        set(v) { sp.edit().putString("gradeSection", v).apply() }

    var schoolYear: String
        get() = sp.getString("schoolYear", "") ?: ""
        set(v) { sp.edit().putString("schoolYear", v).apply() }

    var dismissalTime: String
        get() = sp.getString("dismissalTime", "4:00 PM") ?: "4:00 PM"
        set(v) { sp.edit().putString("dismissalTime", v).apply() }

    var arrivalTemplate: String
        get() = sp.getString("arrivalTemplate",
            "Good day! {NAME} arrived at school at {TIME}. Class ends at {DISMISSAL}. - {TEACHER}, {GRADE} ({SY})"
        ) ?: ""
        set(v) { sp.edit().putString("arrivalTemplate", v).apply() }

    var dismissalTemplate: String
        get() = sp.getString("dismissalTemplate",
            "Heads up! {NAME} was dismissed at {TIME}. See you! - {TEACHER}, {GRADE} ({SY})"
        ) ?: ""
        set(v) { sp.edit().putString("dismissalTemplate", v).apply() }
}
