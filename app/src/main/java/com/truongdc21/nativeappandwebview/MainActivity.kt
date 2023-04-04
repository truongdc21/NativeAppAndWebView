package com.truongdc21.nativeappandwebview

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.truongdc21.nativeappandwebview.Util.FILE_PATH_WEB
import com.truongdc21.nativeappandwebview.Util.NAME_OBJECT_INTERFACE

class MainActivity : AppCompatActivity() {

    private lateinit var edtInputMessage: EditText
    private lateinit var btnButtonSend: Button
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        setupWebView()
    }

    private fun initView() {
        edtInputMessage = findViewById(R.id.edt_message)
        btnButtonSend = findViewById(R.id.btnSendMessage)
        webView = findViewById(R.id.webView)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        webView.apply {
            settings.javaScriptEnabled = true
            addJavascriptInterface(JSInterface(), NAME_OBJECT_INTERFACE)
            loadUrl(FILE_PATH_WEB)
        }
        btnButtonSend.setOnClickListener {
            sendMessageToWebView()
        }
    }

    private fun sendMessageToWebView() {
        webView.evaluateJavascript(
            "javascript: " + "showMessageFromNative(\"" + edtInputMessage.text + "\")",
            null
        )
    }

    inner class JSInterface() {
        @JavascriptInterface
        fun showMessage(message: String) {
            edtInputMessage.setText(message)
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }
}

object Util {
    const val FILE_PATH_WEB = "file:///android_asset/index.html"
    const val NAME_OBJECT_INTERFACE = "JSInterface"
}
