package com.example.twitterclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class WelcomeActivity : AppCompatActivity() {
    private var btnCreate: Button? = null
    private var tvLogin: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        initialize()
    }

    private fun initialize() {
        btnCreate = findViewById<View>(R.id.btn_create) as Button
        tvLogin = findViewById<View>(R.id.tv_login) as TextView

        btnCreate!!.setOnClickListener {
            startActivity(
                Intent(
                    this@WelcomeActivity,
                    CreateAccountActivity::class.java
                )
            )
        }
        tvLogin!!.setOnClickListener{
            startActivity(
                Intent(
                    this@WelcomeActivity,
                    LoginActivity::class.java
                )
            )
        }
    }
}