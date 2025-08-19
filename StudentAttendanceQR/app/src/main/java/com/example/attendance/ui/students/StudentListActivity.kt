package com.example.attendance.ui.students

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.attendance.R
import com.example.attendance.data.db.AppDatabase
import com.example.attendance.data.model.Student
import kotlinx.coroutines.launch

class StudentListActivity : AppCompatActivity() {
    private lateinit var list: ListView
    private var students: List<Student> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students)
        list = findViewById(R.id.listStudents)

        lifecycleScope.launch {
            students = AppDatabase.get(this@StudentListActivity).studentDao().getAll()
            val names = students.map { it.name }
            val adapter = ArrayAdapter(this@StudentListActivity, android.R.layout.simple_list_item_1, names)
            list.adapter = adapter
        }

        list.setOnItemClickListener { _, _, position, _ ->
            val s = students[position]
            val i = Intent(this, StudentDetailActivity::class.java)
            i.putExtra("studentId", s.id)
            startActivity(i)
        }
    }
}
