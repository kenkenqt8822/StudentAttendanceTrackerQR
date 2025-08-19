package com.example.attendance.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.attendance.R
import com.example.attendance.ui.scan.ScannerActivity
import com.example.attendance.ui.settings.SettingsActivity
import com.example.attendance.ui.students.ImportActivity
import com.example.attendance.ui.students.QRGridActivity
import com.example.attendance.ui.students.StudentListActivity

class MainActivity : AppCompatActivity() {

    private val requestPermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { grantResults ->
        val denied = grantResults.filterValues { !it }.keys
        if (denied.isNotEmpty()) {
            Toast.makeText(this, "Permissions denied: $denied", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissions.launch(arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.SEND_SMS
        ))

        findViewById<Button>(R.id.btnImport).setOnClickListener {
            startActivity(Intent(this, ImportActivity::class.java))
        }
        findViewById<Button>(R.id.btnStudents).setOnClickListener {
            startActivity(Intent(this, StudentListActivity::class.java))
        }
        findViewById<Button>(R.id.btnQRs).setOnClickListener {
            startActivity(Intent(this, QRGridActivity::class.java))
        }
        findViewById<Button>(R.id.btnScanArrival).setOnClickListener {
            val i = Intent(this, ScannerActivity::class.java)
            i.putExtra("mode", "ARRIVAL")
            startActivity(i)
        }
        findViewById<Button>(R.id.btnScanDismissal).setOnClickListener {
            val i = Intent(this, ScannerActivity::class.java)
            i.putExtra("mode", "DISMISSAL")
            startActivity(i)
        }
        findViewById<Button>(R.id.btnSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
