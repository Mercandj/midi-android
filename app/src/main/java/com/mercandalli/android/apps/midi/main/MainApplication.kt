package com.mercandalli.android.apps.midi.main

import android.app.Application
import com.mercandalli.android.sdk.midi.main.MidiGraph

/**
 * The [Application] of this project.
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MidiGraph.initialize(this)
    }
}
