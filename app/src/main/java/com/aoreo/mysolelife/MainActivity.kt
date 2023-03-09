package com.aoreo.mysolelife

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Button
import android.widget.Toast
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

//        findViewById<Button>(R.id.logoutBtn).setOnClickListener {
//            auth.signOut()
//            val intent= Intent(this, IntroActivity::class.java)
//            intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//        }
//        findViewById<Button>(R.id.testBtn).setOnClickListener {
//          val packageName : String= "com.skt.tmap.ku"
//            fun isTmapisInstalled(packageName: String,packageManager: PackageManager):Boolean{
//                return try {
//                    packageManager.getPackageInfo(packageName, 0) //사용 가능한 앱을 대상으로 할 경우 flags의 값은 0
//                    true
//                }
//                    catch (ex : PackageManager.NameNotFoundException) {
//                            false
//                    }
//                }
//            val packageManager:PackageManager=packageManager
//            val isTmapInstalled=isTmapisInstalled(packageName,packageManager)
//            if(isTmapInstalled){
//        //        openNavi("유원서초아파트","127.01829132685","37.495640453857")
//                val tmapIntent=packageManager.getLaunchIntentForPackage(packageName)
//                tmapIntent!!.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//                startActivity(tmapIntent)
//            }else{
//                Log.d("OpenNavi","there is no tmap  "+isTmapInstalled)
//
//                Toast.makeText(this,"You need to install TMap",Toast.LENGTH_SHORT).show()
//                val marketLaunch=Intent(Intent.ACTION_VIEW)
//                marketLaunch.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//                marketLaunch.data= Uri.parse("market://details?id=$packageName")
//                startActivity(marketLaunch)
//            }
//            }

        }
    }
