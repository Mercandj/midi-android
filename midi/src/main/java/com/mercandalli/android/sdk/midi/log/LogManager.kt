package com.mercandalli.android.sdk.midi.log

interface LogManager {

    fun log(message: String)

    fun getLog(): List<String>

    fun registerLogListener(listener: LogListener)

    fun unregisterLogListener(listener: LogListener)

    interface LogListener {

        fun onLogChanged()
    }
}
