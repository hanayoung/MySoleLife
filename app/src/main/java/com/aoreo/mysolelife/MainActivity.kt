package com.aoreo.mysolelife

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aoreo.mysolelife.auth.IntroActivity
import com.aoreo.mysolelife.setting.SettingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.skt.Tmap.TMapTapi
import com.skt.Tmap.TMapView
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth= Firebase.auth

        findViewById<ImageView>(R.id.settingBtn).setOnClickListener {
            val intent = Intent(this,SettingActivity::class.java)
            startActivity(intent)
        }
        }
    }
