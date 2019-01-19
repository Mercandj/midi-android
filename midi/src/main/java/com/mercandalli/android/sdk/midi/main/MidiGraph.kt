package com.mercandalli.android.sdk.midi.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import com.mercandalli.android.sdk.midi.log.LogModule
import com.mercandalli.android.sdk.midi.midi_view.MidiActivity
import com.mercandalli.android.sdk.midi.toast.ToastModule

class MidiGraph(
    private val context: Context
) {

    private val midiModuleInternal by lazy { MidiModule(context) }

    private val logManagerInternal by lazy { LogModule().createLogManager() }
    private val midiDeviceInfoManagerInternal by lazy { midiModuleInternal.createMidiDeviceInfoManager() }
    private val midiReceiverInternal by lazy { midiModuleInternal.createMidiReceiver() }
    private val midiSenderInternal by lazy { midiModuleInternal.createMidiSender() }
    private val toastManagerInternal by lazy { ToastModule(context).createToastManager() }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var graph: MidiGraph? = null

        @JvmStatic
        fun getLogManager() = graph?.logManagerInternal!!

        @JvmStatic
        fun getMidiDeviceManager() = graph?.midiDeviceInfoManagerInternal!!

        @JvmStatic
        fun getMidiReceiver() = graph?.midiReceiverInternal!!

        @JvmStatic
        fun getMidiSender() = graph?.midiSenderInternal!!

        @JvmStatic
        fun getToastManager() = graph?.toastManagerInternal!!

        @JvmStatic
        fun isMidiSupported(context: Context) = context.packageManager.hasSystemFeature(
            PackageManager.FEATURE_MIDI
        )

        @JvmStatic
        fun initialize(
            context: Context
        ) {
            if (graph != null) {
                return
            }
            graph = MidiGraph(context.applicationContext)
        }

        @JvmStatic
        fun startMidiActivity() {
            val context = graph?.context
            MidiActivity.start(context!!)
        }
    }
}
