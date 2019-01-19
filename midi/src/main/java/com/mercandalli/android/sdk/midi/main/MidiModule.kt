package com.mercandalli.android.sdk.midi.main

import android.content.Context
import android.content.pm.PackageManager
import java.lang.IllegalStateException
import android.media.midi.MidiManager
import android.os.Handler
import android.os.Looper
import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfoManager
import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfoManagerImpl
import com.mercandalli.android.sdk.midi.receiver.MidiReceiver
import com.mercandalli.android.sdk.midi.receiver.MidiReceiverImpl
import com.mercandalli.android.sdk.midi.sender.MidiSender
import com.mercandalli.android.sdk.midi.sender.MidiSenderImpl

class MidiModule(
    private val context: Context
) {

    private val midiManager by lazy { context.getSystemService(Context.MIDI_SERVICE) as MidiManager }
    private val mainLooper = Looper.getMainLooper()
    private val handler = Handler(mainLooper)

    init {
        if (!MidiGraph.isMidiSupported(context)) {
            throw Exception("Feature not supported on this device")
        }
    }

    fun createMidiDeviceInfoManager(): MidiDeviceInfoManager {
        return MidiDeviceInfoManagerImpl(
            midiManager,
            handler
        )
    }

    fun createMidiReceiver(): MidiReceiver {
        val toastManager = MidiGraph.getToastManager()
        return MidiReceiverImpl(
            midiManager,
            handler,
            toastManager
        )
    }

    fun createMidiSender(): MidiSender {
        val toastManager = MidiGraph.getToastManager()
        return MidiSenderImpl(
            midiManager,
            handler,
            toastManager
        )
    }
}
