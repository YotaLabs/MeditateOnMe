package com.yotalabs.meditateonme.util

import timber.log.Timber

/**
 * @author SashaKhyzhun
 * Created on 1/9/19.
 */
object ThreadAwareTree : Timber.DebugTree() {

    private const val tagFormat = "[%s] %s"

    override fun createStackElementTag(element: StackTraceElement): String? {
        val tag = super.createStackElementTag(element)
        return String.format(tagFormat, Thread.currentThread().name, tag)
    }

}