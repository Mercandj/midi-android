package com.mercandalli.android.sdk.midi.toast

import android.content.Context
import android.widget.Toast
import com.mercandalli.android.sdk.midi.log.LogManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ToastManagerImpl(
    private val context: Context,
    private val logManager: LogManager
) : ToastManager {

    override fun toast(message: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            logManager.log(context.getString(message))
        }
    }

    override fun toast(message: String) {
        GlobalScope.launch(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            logManager.log(message)
        }
    }
}
