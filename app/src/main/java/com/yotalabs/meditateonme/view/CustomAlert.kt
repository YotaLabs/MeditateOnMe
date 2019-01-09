package com.yotalabs.meditateonme.view

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.yotalabs.meditateonme.R

/**
 * @author SashaKhyzhun
 * Created on 1/10/19.
 */
object CustomAlert {

    fun createNoInternetDialog(
        /**
         * Context
         */
        ctx: Context,
        /**
         * Title
         */
        title: Int,
        /**
         * Message
         */
        message: Int?,
        /**
         * Positive button title
         */
        buttonPos: Int,
        /**
         * Negative button title
         */
        buttonNeg: Int,
        /**
         * Action for positive click
         */
        actionPositive: () -> Unit,
        /**
         * Action for negative click
         */
        actionNegative: () -> Unit

    ): AlertDialog {
        return AlertDialog.Builder(ctx)
            .setTitle(title)
            .setMessage(message ?: R.string.empty_text)
            .setNegativeButton(buttonNeg) { dialog, _ -> actionNegative(); dialog.cancel() }
            .setPositiveButton(buttonPos) { dialog, _ -> actionPositive(); dialog.cancel() }
            .show()
    }

}