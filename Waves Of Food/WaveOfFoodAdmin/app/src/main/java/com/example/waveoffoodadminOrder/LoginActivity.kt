package com.example.waveoffoodadminOrder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.waveoffoodadminOrder.databinding.ActivityLoginBinding
import com.example.waveoffoodadminOrder.model.LoginModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private lateinit var database: FirebaseDatabase
    private var login = LoginModel()
    private var isLogin: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()

        checkLoginRegister()

        binding.apply {

            btLogin.setOnClickListener {
                if (binding.btLogin.text.toString() == "Add New") {
                    checkRegisterInput()
                } else if (login.email == binding.etEmail.text.toString() && login.password == binding.etPassword.text.toString()) {
                    val sharedPreferences= getSharedPreferences("LOGIN_REF", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("LOGIN_REF", true)
                    editor.apply()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun checkRegisterInput() {
        if (binding.etEmail.text.toString().isEmpty()) {
            binding.etEmail.error = "Empty"
        } else if (binding.etPassword.text.toString().isEmpty()) {
            binding.etPassword.error = "Empty"
        } else {
            createLogin()
        }
    }

    private fun checkLoginRegister() {

        database.reference.child("LOGIN_REF").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    login = dataSnapshot.getValue(LoginModel::class.java)!!
                    Log.d("checklogins", "onDataChange: $login")

                } else {
                    binding.btLogin.text = "Add New"
                    Toast.makeText(
                        this@LoginActivity, "Please Register to Login", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

                Toast.makeText(this@LoginActivity, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()

            }
        })
    }



    private fun createLogin() {
        val login = LoginModel(
            "LOGIN_REF",
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString(),
            "",
            "",
            ""
        )

        database.reference.child("LOGIN_REF").setValue(login).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                binding.btLogin.text = "Login"
                checkLoginRegister()
                Toast.makeText(this@LoginActivity, "Login Registered", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@LoginActivity, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()


            }
        }
    }
}