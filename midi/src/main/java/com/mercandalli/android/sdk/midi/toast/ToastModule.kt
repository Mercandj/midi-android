package com.mercandalli.android.sdk.midi.toast

import android.content.Context

class ToastModule(
    val context: Context
) {

    fun createToastManager(): ToastManager {
        return ToastManagerImpl(
            context.applicationContext
        )
    }
}
