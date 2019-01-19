package com.mercandalli.android.sdk.midi.sender

import android.media.midi.MidiManager
import android.os.Handler
import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfo
import com.mercandalli.android.sdk.midi.toast.ToastManager

class MidiSenderImpl(
    private val midiManager: MidiManager,
    private val handler: Handler,
    private val toastManager: ToastManager
) : MidiSender {

    override fun send(
        midiDeviceInfo: MidiDeviceInfo
    ) {
        midiManager.openDevice(
            midiDeviceInfo.androidMidiInfo,
            {
                val inputPort = it.openInputPort(0)
                if (inputPort == null) {
                    toastManager.toast("Device open but port closed")
                    return@openDevice
                } else {
                    toastManager.toast("Device open and port open")
                }
                val buffer = ByteArray(32)
                var numBytes = 0
                val channel = 3 // MIDI channels 1-16 are encoded as 0-15.
                buffer[numBytes++] = (0x90 + (channel - 1)).toByte() // note on
                buffer[numBytes++] = 60.toByte() // pitch is middle C
                buffer[numBytes++] = 127.toByte() // max velocity
                val offset = 0
                // post is non-blocking
                inputPort.send(buffer, offset, numBytes)
            },
            handler
        )
    }

}
