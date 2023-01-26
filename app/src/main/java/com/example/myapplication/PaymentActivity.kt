package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.Toolbar

class PaymentActivity : AppCompatActivity() {

    lateinit var webView: WebView
    companion object {
        const val URL = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { super.onBackPressed() }

        webView = findViewById(R.id.WebView)
        webView.webViewClient = WebViewClient()
        webView.loadUrl(URL)

        // web settings
        val webSettings = webView.settings
        // enable javascript
        webSettings.javaScriptEnabled = true

        // enable other tools, such boostrap
        webSettings.domStorageEnabled = true

    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}