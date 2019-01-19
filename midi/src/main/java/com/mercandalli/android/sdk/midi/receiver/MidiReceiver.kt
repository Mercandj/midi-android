package com.mercandalli.android.sdk.midi.receiver

import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfo

interface MidiReceiver {

    fun listen(midiDeviceInfo: MidiDeviceInfo)

    fun registerMidiListener(listener: MidiListener)

    fun unregisterMidiListener(listener: MidiListener)

    interface MidiListener {

        fun onReceived()
    }
}
