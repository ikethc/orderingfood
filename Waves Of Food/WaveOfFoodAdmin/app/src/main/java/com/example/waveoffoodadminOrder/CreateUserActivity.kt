package com.example.waveoffoodadminOrder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.waveoffoodadminOrder.databinding.ActivityCreateUserBinding
import com.example.waveoffoodadminOrder.model.LoginModel
import com.google.firebase.database.FirebaseDatabase

class CreateUserActivity : AppCompatActivity() {
    private val binding: ActivityCreateUserBinding by lazy {
        ActivityCreateUserBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.createUserButton.setOnClickListener {
            if(binding.email.text.toString().isEmpty() || binding.passsword.text.toString().isEmpty() || binding.Name.text.toString().isEmpty()) {

                Toast.makeText(this@CreateUserActivity, "Fill All Data", Toast.LENGTH_SHORT).show()
            } else {
                createNewLogin()
            }
        }

    }

    private fun createNewLogin() {
        Toast.makeText(this@CreateUserActivity, "Please Wait...", Toast.LENGTH_SHORT).show()
        val login = LoginModel(
            "LOGIN_REF",
            binding.email.text.toString(),
            binding.passsword.text.toString(),
            binding.Name.text.toString(),
            "",
            ""
        )

        database.reference.child("LOGIN_REF").setValue(login).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@CreateUserActivity, "New User Added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@CreateUserActivity, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()


            }
        }
    }

}

