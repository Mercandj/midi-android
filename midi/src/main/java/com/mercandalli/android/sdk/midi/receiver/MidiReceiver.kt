package com.mercandalli.android.sdk.midi.receiver

import android.support.annotation.IntRange
import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfo

interface MidiReceiver {

    fun listen(midiDeviceInfo: MidiDeviceInfo)

    fun close()

    fun registerMidiListener(listener: MidiListener)

    fun unregisterMidiListener(listener: MidiListener)

    interface MidiListener {

        fun onReceived(
            @IntRange(from = 21, to = 108) midiCode: Int,
            @IntRange(from = 0, to = 127) velocity: Int
        )
    }
}
