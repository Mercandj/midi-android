package com.mercandalli.android.apps.midi.activity

import android.app.Activity
import android.support.annotation.IdRes
import android.view.View

object ActivityExtension {

    fun <T : View> Activity.bind(@IdRes res: Int): Lazy<T> {
        @Suppress("UNCHECKED_CAST")
        return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(res) }
    }
}
