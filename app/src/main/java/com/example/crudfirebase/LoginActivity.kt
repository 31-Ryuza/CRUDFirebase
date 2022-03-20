package com.example.crudfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Toast.makeText(this, "Silahkan login :)", Toast.LENGTH_SHORT).show()

        val btn = findViewById<Button>(R.id.btn_login)
        val username_id = findViewById<EditText>(R.id.input_username)
        val password_id = findViewById<EditText>(R.id.input_password)

        btn.setOnClickListener{
            val username = username_id.text.toString()
            when(username) {
                in "" -> Toast.makeText(applicationContext, "Username / Password harus diisi", Toast.LENGTH_SHORT).show()
                in "admin" -> this.startActivity(Intent(this,MainActivity::class.java))
                in "admin" -> Toast.makeText(applicationContext, "HALLO SELAMAT DATANG", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(applicationContext, "Username / Password anda salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}