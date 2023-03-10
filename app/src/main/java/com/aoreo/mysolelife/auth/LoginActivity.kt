package com.aoreo.mysolelife.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.aoreo.mysolelife.MainActivity
import com.aoreo.mysolelife.R
import com.aoreo.mysolelife.contentsList.ContentShowActivity
import com.aoreo.mysolelife.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.loginBtn.setOnClickListener {
            val email = binding.emailArea.text.toString()
            val pwd = binding.pwdArea.text.toString()
            auth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                    } else {

                    }
                }
        }

    }
}