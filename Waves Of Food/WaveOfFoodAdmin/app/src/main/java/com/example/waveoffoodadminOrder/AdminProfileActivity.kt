package com.example.waveoffoodadminOrder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.waveoffoodadminOrder.databinding.ActivityAdminProfileBinding
import com.example.waveoffoodadminOrder.model.LoginModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminProfileActivity : AppCompatActivity() {
    private val binding: ActivityAdminProfileBinding by lazy { ActivityAdminProfileBinding.inflate(layoutInflater) }
    private lateinit var database: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()

        fetchLoginData()

        binding.backButton.setOnClickListener {
            finish()
        }
        binding.saveInfoButton.setOnClickListener {
            if(binding.email.text.toString().isEmpty() || binding.password.text.toString().isEmpty() || binding.name.text.toString().isEmpty()
                || binding.phone.text.toString().isEmpty() || binding.address.text.toString().isEmpty()) {

                Toast.makeText(this@AdminProfileActivity, "Fill All Data", Toast.LENGTH_SHORT).show()
            } else {
                updateUserData()
            }
        }
        binding.name.isEnabled = false
        binding.address.isEnabled = false
        binding.email.isEnabled = false
        binding.phone.isEnabled = false
        binding.password.isEnabled = false
        binding.saveInfoButton.isEnabled = false

        var isEnable = false
        binding.editButton.setOnClickListener {
            isEnable = !isEnable
            binding.name.isEnabled = isEnable
            binding.address.isEnabled = isEnable
            binding.email.isEnabled = isEnable
            binding.phone.isEnabled = isEnable
            binding.password.isEnabled = isEnable
            if (isEnable) {
                binding.name.requestFocus()
            }
            binding.saveInfoButton.isEnabled = isEnable
        }

    }

    private fun fetchLoginData() {
        val loginReference = database.getReference("LOGIN_REF")

        loginReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val login = dataSnapshot.getValue(LoginModel::class.java)
                    if (login != null) {
                        binding.name.setText(login.name)
                        binding.address.setText(login.address)
                        binding.email.setText(login.email)
                        binding.phone.setText(login.phone)
                        binding.password.setText("")
                        binding.saveInfoButton.isEnabled = true

                    } else {
                        Log.d("LoginData", "Login data is null")
                    }
                } else {
                    Log.d("LoginData", "Login data does not exist")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

                Log.d("LoginData", "Error fetching login data: ${databaseError.message}")
            }
        })
    }


    private fun updateUserData() {
        val login = LoginModel(
            "LOGIN_REF",
            binding.email.text.toString(),
            binding.password.text.toString(),
            binding.name.text.toString(),
            binding.address.text.toString(),
            binding.phone.text.toString(),
        )

        database.reference.child("LOGIN_REF").setValue(login).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@AdminProfileActivity, "Admin profile Updated", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@AdminProfileActivity, MainActivity::class.java))
            } else {
                Toast.makeText(this@AdminProfileActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }




}