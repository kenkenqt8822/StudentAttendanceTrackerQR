package com.example.attendance.ui.students

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.attendance.R
import com.example.attendance.data.db.AppDatabase
import com.example.attendance.data.model.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.InputStream

class ImportActivity: AppCompatActivity() {

    private val pickFile = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            importExcel(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import)
        findViewById<Button>(R.id.btnPick).setOnClickListener {
            pickFile.launch(arrayOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
        }
    }

    private fun importExcel(uri: Uri) {
        lifecycleScope.launch {
            val students = withContext(Dispatchers.IO) {
                val list = mutableListOf<Student>()
                val ins: InputStream? = contentResolver.openInputStream(uri)
                ins.use { input ->
                    val wb = XSSFWorkbook(input)
                    val sheet = wb.getSheetAt(0)
                    // Expect header row: Name | LRN | ParentName | ParentPhone
                    for (i in 1..sheet.lastRowNum) {
                        val row = sheet.getRow(i) ?: continue
                        val name = row.getCell(0)?.stringCellValue ?: continue
                        val lrn = row.getCell(1)?.toString() ?: continue
                        val pName = row.getCell(2)?.stringCellValue ?: ""
                        val pPhone = row.getCell(3)?.toString() ?: ""
                        list.add(Student(name = name.trim(), lrn = lrn.trim(), parentName = pName.trim(), parentPhone = pPhone.trim()))
                    }
                    wb.close()
                }
                list
            }
            if (students.isNotEmpty()) {
                AppDatabase.get(this@ImportActivity).studentDao().insertAll(students)
                Toast.makeText(this@ImportActivity, "Imported ${students.size} students", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this@ImportActivity, "No rows imported.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
