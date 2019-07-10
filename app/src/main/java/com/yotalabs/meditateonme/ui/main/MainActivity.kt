package com.yotalabs.meditateonme.ui.main

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.yotalabs.meditateonme.R
import com.yotalabs.meditateonme.util.isOnline
import com.yotalabs.meditateonme.view.CustomAlert
import kotlinx.android.synthetic.main.main_activity.*
import timber.log.Timber


/**
 * @author SashaKhyzhun
 * Created on 1/9/19.
 */
class MainActivity : AppCompatActivity() {


    companion object {
        const val URL = "https://meditateonme.com/"
        const val PUSH_NOTIFICATIONS = "push-notification/"
        const val TOKEN = "?token="
        const val whatsApp_prefix = "whatsapp://"
        const val inProgressText = "Loading..."
        const val TAG = "MainActivity"
        fun getIntent(context: Context?) = Intent(context, MainActivity::class.java)
    }


    private var internetDialog: AlertDialog? = null
    private var deviceToken = ""
    private var fullLink = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        FirebaseInstanceId
            .getInstance()
            .instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                Timber.d("TOKEN = ${task.result?.token}")
                if (!task.isSuccessful) {
                    checkInternetSettings()
                    return@OnCompleteListener
                }
                fullLink = URL + PUSH_NOTIFICATIONS + TOKEN + task.result!!.token
                Timber.d("fullLink=$fullLink")
                checkInternetSettings()
            })


        with(supportActionBar) {
            this?.setDisplayShowHomeEnabled(true)
            this?.hide()
            this
        }

        home.setOnClickListener {
            initWebPage() // would be better to call @checkInternetSettings.
        }
    }


    override fun onResume() {
        super.onResume()
        if (internetDialog != null && internetDialog!!.isShowing) {
            internetDialog?.dismiss()
        }
        checkInternetSettings()

    }

    private fun checkInternetSettings() {
        if (isOnline(this)) {
            initWebPage()
            return
        }

        internetDialog = CustomAlert.createNoInternetDialog(
            ctx = this,
            title = R.string.dialog_internet_title,
            message = R.string.dialog_internet_message,
            buttonPos = R.string.dialog_button_positive,
            buttonNeg = R.string.dialog_button_negative,
            actionPositive = {
                // clicked "Yes"
                startActivity(Intent(Settings.ACTION_SETTINGS))
            },
            actionNegative = {
                // clicked "No"
                createExitDialog(this)
            })
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView?.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webView?.restoreState(savedInstanceState)
    }


    @Suppress("DEPRECATION")
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebPage() {
        webView.webChromeClient = object : WebChromeClient() {

            private var mProgress: ProgressDialog? = null

            override fun onProgressChanged(view: WebView, progress: Int) {
                if (mProgress == null) {
                    mProgress = ProgressDialog(this@MainActivity)
                    if (layout_splash.visibility == View.GONE) {
                        mProgress?.show()
                    }
                }
                mProgress?.setMessage(inProgressText)

                if (progress > 35) {
                    if (layout_splash.visibility == View.GONE) {
                        mProgress?.dismiss()
                    } else {
                        layout_splash.visibility = View.GONE
                        mProgress = null
                    }
                }
            }
        }


        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.loadUrl(fullLink)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                return if (url != null && url.startsWith(whatsApp_prefix)) {
                    view.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    true
                } else {
                    false
                }
            }
        }
    }


    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }


    private fun createExitDialog(ctx: Context) {
        CustomAlert.createNoInternetDialog(
            ctx = ctx,
            title = R.string.dialog_exit_title,
            message = null,
            buttonPos = R.string.dialog_button_positive,
            buttonNeg = R.string.dialog_button_negative,
            actionPositive = {
                finish()
            },
            actionNegative = {
                checkInternetSettings()
            }
        )
    }

}