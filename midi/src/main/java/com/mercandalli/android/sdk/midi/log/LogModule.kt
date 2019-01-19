package com.mercandalli.android.sdk.midi.log

class LogModule {

    fun createLogManager(): LogManager {
        return LogManagerImpl()
    }
}
