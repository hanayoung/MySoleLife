package com.aoreo.mysolelife.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.aoreo.mysolelife.MainActivity
import com.aoreo.mysolelife.R
import com.aoreo.mysolelife.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth

class JoinActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var binding:ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isGoToJoin=true
        auth = FirebaseAuth.getInstance()

        binding=DataBindingUtil.setContentView(this,R.layout.activity_join)

        binding.joinBtn.setOnClickListener {

            val email = binding.joinEmailArea.text.toString()
            val pwd = binding.joinPwdArea.text.toString()
            val check = binding.joinCheckArea.text.toString()

            Log.d("emaillength",email.length.toString())
            if((email.length.toString()) == "0"){
                Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
                Log.d("emaillength",email.length.toString())
                isGoToJoin=false

            }
            if(!pwd.equals(check)){
                isGoToJoin=false
                Toast.makeText(this,"Diff",Toast.LENGTH_SHORT).show()
            }
            if(isGoToJoin){
                Log.d("in",email+pwd)
                auth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()

                        val intent=Intent(this,MainActivity::class.java)
                        intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }
}