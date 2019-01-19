package com.mercandalli.android.sdk.midi.sender

import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfo

interface MidiSender {

    fun send(
        midiDeviceInfo: MidiDeviceInfo
    )
}
