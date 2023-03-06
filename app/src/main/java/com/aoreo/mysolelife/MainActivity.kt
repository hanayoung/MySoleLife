package com.aoreo.mysolelife

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.aoreo.mysolelife.auth.IntroActivity
import com.google.firebase.auth.FirebaseAuth
import com.skt.Tmap.TMapTapi
import com.skt.Tmap.TMapView
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth= FirebaseAuth.getInstance()
        val tmapview = TMapView(this)
        tmapview.setSKTMapApiKey("7yod3BNAYGa28IlV2E5vs31ZVtpzQZ1ZaTyTCW8Y")

        fun openNavi(name: String,longitude:String,latitude:String){
            val tMapTapi = TMapTapi(this)
            val isTMapApp = tMapTapi.isTmapApplicationInstalled
            if (isTMapApp) {
                val pathInfo = HashMap<String, String>()
                pathInfo.put("rGoName", name)
                pathInfo.put("rGoX", longitude)
                pathInfo.put("rGoY", latitude)
                tMapTapi.invokeRoute(pathInfo)
            } else {
               Log.d("OpenNavi","cannot open navi")
            }
        }

        findViewById<Button>(R.id.logoutBtn).setOnClickListener {
            auth.signOut()
            val intent= Intent(this, IntroActivity::class.java)
            intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        findViewById<Button>(R.id.testBtn).setOnClickListener {
            val tmaptapi=TMapTapi(this)
            val isTmapInstalled=tmaptapi.isTmapApplicationInstalled
            if(isTmapInstalled==true){
                openNavi("유원서초아파트","127.01829132685","37.495640453857")
            }else{
                Log.d("OpenNavi","there is no tmap  "+isTmapInstalled)
            }
        }
    }
}