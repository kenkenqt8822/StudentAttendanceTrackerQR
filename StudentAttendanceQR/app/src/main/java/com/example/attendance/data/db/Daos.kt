package com.example.attendance.data.db

import androidx.room.*
import com.example.attendance.data.model.Student
import com.example.attendance.data.model.AttendanceLog

@Dao
interface StudentDao {
    @Query("SELECT * FROM students ORDER BY name")
    suspend fun getAll(): List<Student>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(students: List<Student>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(student: Student): Long

    @Query("SELECT * FROM students WHERE id = :id")
    suspend fun getById(id: Long): Student?

    @Query("SELECT * FROM students WHERE lrn = :lrn LIMIT 1")
    suspend fun getByLRN(lrn: String): Student?
}

@Dao
interface AttendanceDao {
    @Insert
    suspend fun insert(log: AttendanceLog): Long
}
