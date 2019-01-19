package com.mercandalli.android.sdk.midi.toast

import android.content.Context
import com.mercandalli.android.sdk.midi.main.MidiGraph

class ToastModule(
    val context: Context
) {

    fun createToastManager(): ToastManager {
        val logManager = MidiGraph.getLogManager()
        return ToastManagerImpl(
            context.applicationContext,
            logManager
        )
    }
}
