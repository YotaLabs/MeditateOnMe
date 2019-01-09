package com.yotalabs.meditateonme.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import com.yotalabs.meditateonme.R
import android.os.Handler
import com.yotalabs.meditateonme.ui.main.MainActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        Handler().postDelayed({
            startActivity(MainActivity.getIntent(this))
            finish()
        }, timeout)

    }


    companion object {
        const val timeout = 1000L

        fun getIntent(context: Context?) = Intent(context, SplashActivity::class.java)
    }

}
