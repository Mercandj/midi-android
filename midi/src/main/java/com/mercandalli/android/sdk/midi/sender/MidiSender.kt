package com.mercandalli.android.sdk.midi.sender

import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfo
import android.support.annotation.IntRange

interface MidiSender {

    fun send(
        midiDeviceInfo: MidiDeviceInfo,
        @IntRange(from = 21, to = 108) midiCode: Int = 21,
        @IntRange(from = 0, to = 127) velocity: Int = 127,
        oneTime: Boolean = true
    )

    fun close()
}
