package com.aoreo.mysolelife.contentsList

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import com.aoreo.mysolelife.R
import java.util.*

class ContentShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_show)
        val toWebViewBtn = findViewById<Button>(R.id.toWebViewBtn)
        val getUrl = intent.getStringExtra("url")
        Toast.makeText(this, getUrl, Toast.LENGTH_SHORT).show()


        val webView: WebView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
//        webView.loadUrl(getUrl.toString())
//        webView.loadUrl("file:///android_asset/webviewtest.html")

        webView.addJavascriptInterface(WebAppInterface(this), "Android")
        toWebViewBtn.setOnClickListener {
            val packageName: String = "com.skt.tmap.ku"
            fun isTmapisInstalled(packageName: String, packageManager: PackageManager): Boolean {
                return try {
                    packageManager.getPackageInfo(packageName, 0) //사용 가능한 앱을 대상으로 할 경우 flags의 값은 0
                    true
                } catch (ex: PackageManager.NameNotFoundException) {
                    false
                }
            }

            val packageManager: PackageManager = packageManager
            val isTmapInstalled = isTmapisInstalled(packageName, packageManager)
            if (isTmapInstalled) {
                val tmapIntent = packageManager.getLaunchIntentForPackage(packageName)
                tmapIntent!!.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(tmapIntent)
            } else {
                val callback= ValueCallback<String> { result ->
                }
                Log.d("OpenNavi", "there is no tmap  " + isTmapInstalled)
//                    webView.loadUrl("javascript:isInstalled(false)")
                val parameter = "hello from kotlin!"
                webView.webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        val parameter = "hello from kotlin!"
                        webView.evaluateJavascript("HelloWorldComponent.setup().myVueFunction(`$parameter`);",callback) // call the function
                    }
                }
                webView.loadUrl("http://172.30.1.15:8080")
//                webView.evaluateJavascript("HelloWorldComponent.setup().myVueFunction();",callback)
            } // 네이티브에서 자바스크립트 함수 호출 -> 네이티브에서 웹으로 값 전달
        }
    }
}

class WebAppInterface(private val mContext: Context) {
    @JavascriptInterface
    fun showToast(toast: String) {
        Log.d("From Toast", toast)
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun installTMap() {
        val packageName: String = "com.skt.tmap.ku"
        val marketLaunch = Intent(Intent.ACTION_VIEW)
        marketLaunch.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        marketLaunch.data = Uri.parse("market://details?id=$packageName")
        mContext.startActivity(marketLaunch)
    }
}