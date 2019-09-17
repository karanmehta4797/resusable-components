package com.example.resusablecomponents

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.helpers.utilities.NetworkHelper
import com.example.resusablecomponents.utilities.WebViewHelper
import com.example.selfinspection.utilities.PreferenceHelper
import com.google.android.material.snackbar.Snackbar

class WebViewActivity : AppCompatActivity() {

    private lateinit var txtError: TextView
    private lateinit var relativeLayout: RelativeLayout
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        init()

        loadUrl()
    }

    private fun init() {
        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)
        relativeLayout = findViewById(R.id.relativeLayout)
        txtError = findViewById(R.id.txtInternetOff)
        txtError.setOnClickListener {
            loadUrl()
        }
    }

    private fun loadUrl() {
        if (NetworkHelper.isInternetAvailable(this@WebViewActivity)) {
            progressBar.visibility = View.VISIBLE
            WebViewHelper.loadWebView(
                webView,
                Constants.WEBVIEW_URL,
                progressBar,
                txtError
            )
        } else {
            progressBar.visibility = View.GONE
            txtError.visibility = View.VISIBLE
            Snackbar.make(
                relativeLayout,
                getString(R.string.INTERNET_OFF_SNACKBAR),
                Constants.SNACKBAR_LENGTH
            ).show()
        }
    }

    private fun restartActivity() {
        finish()
        startActivity(getIntent())
    }
}
