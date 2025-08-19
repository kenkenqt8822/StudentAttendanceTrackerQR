package com.example.attendance.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance_logs")
data class AttendanceLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentId: Long,
    val timestamp: Long,
    val type: String // ARRIVAL or DISMISSAL
)
