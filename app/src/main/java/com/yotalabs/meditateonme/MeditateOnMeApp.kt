package com.yotalabs.meditateonme

import android.app.Application
import com.yotalabs.meditateonme.BuildConfig
import com.yotalabs.meditateonme.util.ThreadAwareTree
import timber.log.Timber

/**
 * @author SashaKhyzhun
 * Created on 1/9/19.
 */
class MeditateOnMeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(ThreadAwareTree)
        }
        Timber.d("Application is created")
    }

}