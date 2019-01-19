package com.mercandalli.android.sdk.midi.log

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LogManagerImpl : LogManager {

    private val logs = ArrayList<String>()
    private val logListeners = ArrayList<LogManager.LogListener>()

    override fun log(message: String) {
        GlobalScope.launch(Dispatchers.Main) {
            logs.add(message)
            for (listener in logListeners) {
                listener.onLogChanged()
            }
        }
    }

    override fun getLog() = ArrayList<String>(logs)

    override fun registerLogListener(listener: LogManager.LogListener) {
        if (logListeners.contains(listener)) {
            return
        }
        logListeners.add(listener)
    }

    override fun unregisterLogListener(listener: LogManager.LogListener) {
        logListeners.remove(listener)
    }
}
