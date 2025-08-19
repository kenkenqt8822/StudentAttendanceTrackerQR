package com.example.attendance.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val lrn: String,
    val parentName: String,
    val parentPhone: String
)
