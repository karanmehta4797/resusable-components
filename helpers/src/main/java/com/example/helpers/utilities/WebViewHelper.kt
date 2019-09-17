package com.example.helpers.utilities

import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView

open class WebViewHelper {

    companion object {

        //To load URL in webview
        fun loadWebView(webView: WebView, url: String, txtError: TextView) {

            webView.loadUrl(url)
            val settings = webView.settings
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true)
            settings.setAppCacheEnabled(true)
            settings.loadsImagesAutomatically = true

            txtError.visibility = View.GONE
            val webViewClient: WebViewClient = object : WebViewClient() {

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)

                    webView.visibility = View.GONE
                    txtError.visibility = View.VISIBLE
                }
            }

            webView.webViewClient = webViewClient
        }

        //To load URL in webview and show progressbar till page loads
        fun loadWebView(
            webView: WebView,
            url: String,
            progressBar: ProgressBar,
            txtError: TextView
        ) {

            webView.loadUrl(url)
            val settings = webView.settings
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true)
            settings.setAppCacheEnabled(true)
            settings.loadsImagesAutomatically = true

            txtError.visibility = View.GONE

            val webViewClient: WebViewClient = object : WebViewClient() {

                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    super.onPageCommitVisible(view, url)

                    progressBar.visibility = View.INVISIBLE
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    webView.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    txtError.visibility = View.VISIBLE
                }
            }
            webView.webViewClient = webViewClient
        }
    }
}