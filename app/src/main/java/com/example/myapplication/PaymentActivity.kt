package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class PaymentActivity : AppCompatActivity() {

    companion object {
        const val URL = ""
    }

    lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val url = intent.getStringExtra(URL)

        toolbar.setNavigationOnClickListener { super.onBackPressed() }

        webView = findViewById(R.id.WebView)
        webView.webViewClient = WebViewClient()

        webView.loadUrl("$url#/payment-list")

        Toast.makeText(this, url, Toast.LENGTH_LONG).show()
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