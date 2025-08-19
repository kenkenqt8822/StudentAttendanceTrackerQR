package com.example.attendance.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.attendance.data.model.AttendanceLog
import com.example.attendance.data.model.Student

@Database(entities = [Student::class, AttendanceLog::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun attendanceDao(): AttendanceDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "attendance.db"
                ).build().also { INSTANCE = it }
            }
    }
}
