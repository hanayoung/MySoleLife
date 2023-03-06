package com.aoreo.mysolelife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.aoreo.mysolelife.auth.IntroActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= FirebaseAuth.getInstance()

        setContentView(R.layout.activity_splash)

        if(auth.currentUser?.uid==null){
            Log.d("Splash Activity","null")
            Handler().postDelayed({
                startActivity(Intent(this, IntroActivity::class.java))
                finish()
            },3000)
        }else{
            Log.d("Splash Activity","not null")
            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            },3000)
        }

    }

}