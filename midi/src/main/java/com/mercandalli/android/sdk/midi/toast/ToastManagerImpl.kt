package com.mercandalli.android.sdk.midi.toast

import android.content.Context
import android.widget.Toast

class ToastManagerImpl(
    private val context: Context
) : ToastManager {

    override fun toast(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
