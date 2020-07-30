package com.example.twitterclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class CreateAccountActivity : AppCompatActivity() {
    private val tag = "CreateAccountActivity"
    private var name: String? = null
    private var email: String? = null
    private var password: String? = null

    private var etName: EditText? = null
    private var etUsername: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnCreateAccount: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        initialize()
    }

    private fun initialize() {
        etName = findViewById<View>(R.id.et_name) as EditText
        etUsername = findViewById<View>(R.id.et_username) as EditText
        etEmail = findViewById<View>(R.id.et_email) as EditText
        etPassword = findViewById<View>(R.id.et_password) as EditText
        btnCreateAccount = findViewById<View>(R.id.btn_create_account) as Button

        btnCreateAccount!!.setOnClickListener {
            createNewAccount()
        }
    }

    private fun createNewAccount() {
        name = etName?.text.toString()
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(tag, "createUserWithEmail:success")
                        verifyEmail()
                        updateUserInfoAndUI()
                    } else {
                        Log.w(tag, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            this@CreateAccountActivity, "Registreerimine ebaõnnestunud.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUserInfoAndUI() {
        //start next activity
        val intent = Intent(this@CreateAccountActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail() {
        val mUser = FirebaseAuth.getInstance().currentUser
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@CreateAccountActivity,
                        "Confirmation email sent" + mUser.email,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.e(tag, "sendEmailVerification", task.exception)
                    Toast.makeText(
                        this@CreateAccountActivity,
                        "Confirmation email sending failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}