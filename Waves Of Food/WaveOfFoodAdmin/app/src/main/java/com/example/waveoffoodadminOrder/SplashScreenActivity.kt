package com.example.waveoffoodadminOrder

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val sharedPreferences = getSharedPreferences("LOGIN_REF", MODE_PRIVATE)
        val isLogin = sharedPreferences.getBoolean("LOGIN_REF", false)

       if(isLogin) {
           Handler(Looper.getMainLooper()).postDelayed({
               val intent = Intent(this, MainActivity::class.java)
               startActivity(intent)
               finish()
           },3000)
       } else {
           Handler(Looper.getMainLooper()).postDelayed({
               val intent = Intent(this, LoginActivity::class.java)
               startActivity(intent)
               finish()
           },3000)
       }
    }
}