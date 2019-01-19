package com.mercandalli.android.sdk.midi.main

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.mercandalli.android.sdk.midi.toast.ToastModule

class MidiGraph(
    private val context: Context
) {

    private val midiModuleInternal by lazy { MidiModule(context) }

    private val midiDeviceInfoManagerInternal by lazy { midiModuleInternal.createMidiDeviceInfoManager() }
    private val midiReceiverInternal by lazy { midiModuleInternal.createMidiReceiver() }
    private val midiSenderInternal by lazy { midiModuleInternal.createMidiSender() }
    private val toastManagerInternal by lazy { ToastModule(context).createToastManager() }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private lateinit var graph: MidiGraph

        fun getMidiDeviceManager() = graph.midiDeviceInfoManagerInternal
        fun getMidiReceiver() = graph.midiReceiverInternal
        fun getMidiSender() = graph.midiSenderInternal
        fun getToastManager() = graph.toastManagerInternal

        fun initialize(
            application: Application
        ) {
            graph =
                MidiGraph(application)
        }

    }
}
