package com.aoreo.mysolelife.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.databinding.DataBindingUtil
import com.aoreo.mysolelife.MainActivity
import com.aoreo.mysolelife.R
import com.aoreo.mysolelife.databinding.ActivityIntroBinding
import com.google.firebase.auth.FirebaseAuth

class IntroActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIntroBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_intro)
        auth= FirebaseAuth.getInstance()

        binding=DataBindingUtil.setContentView(this,R.layout.activity_intro)

        binding.loginBtn.setOnClickListener {
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.joinBtn.setOnClickListener {
            val intent=Intent(this,JoinActivity::class.java)
            startActivity(intent)
        }

        binding.noAccountBtn.setOnClickListener {
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent=Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this,"익명 로그인 성공",Toast.LENGTH_SHORT).show()
                    } else {
                       Toast.makeText(this,"로그인 실패",Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}