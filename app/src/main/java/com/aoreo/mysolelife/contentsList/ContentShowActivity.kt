package com.aoreo.mysolelife.contentsList

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64.encodeToString

import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import com.aoreo.mysolelife.R
import java.util.*

class ContentShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_show)

        val getUrl=intent.getStringExtra("url")
        Toast.makeText(this,getUrl,Toast.LENGTH_SHORT).show()

        val webView : WebView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled=true
//        webView.loadUrl(getUrl.toString())
        webView.loadUrl("file:///android_asset/webviewtest.html")
        webView.addJavascriptInterface(WebAppInterface(this),"Android")

    }
}
class WebAppInterface(private val mContext:Context){
    @JavascriptInterface
    fun showToast(){
        Toast.makeText(mContext,"hello i'm from app! ",Toast.LENGTH_SHORT).show()
    }
}