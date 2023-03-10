package com.aoreo.mysolelife.contentsList

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.*
import android.widget.Button
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.aoreo.mysolelife.R
import java.util.concurrent.Executor

class ContentShowActivity : AppCompatActivity() {
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var webView : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_show)

        val getUrl = intent.getStringExtra("url")
        Toast.makeText(this, getUrl, Toast.LENGTH_SHORT).show()


        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true

//        webView.addJavascriptInterface(WebAppInterface(this), "Android")
        webView.loadUrl(getUrl.toString())
//        webView.loadUrl("file:///android_asset/webviewtest.html")
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