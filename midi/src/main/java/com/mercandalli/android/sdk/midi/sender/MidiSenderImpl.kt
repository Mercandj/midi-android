package com.mercandalli.android.sdk.midi.sender

import android.media.midi.MidiInputPort
import android.media.midi.MidiManager
import android.os.Handler
import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfo
import com.mercandalli.android.sdk.midi.toast.ToastManager
import android.support.annotation.IntRange

class MidiSenderImpl(
    private val midiManager: MidiManager,
    private val handler: Handler,
    private val toastManager: ToastManager
) : MidiSender {

    private var inputPort: MidiInputPort? = null
    private var midiDeviceInfo: MidiDeviceInfo? = null

    override fun connect(midiDeviceInfo: MidiDeviceInfo) {
        if (this.midiDeviceInfo?.serialNumber != midiDeviceInfo.serialNumber) {
            toastManager.toast("Already connected in ram")
            return
        }
        midiManager.openDevice(
            midiDeviceInfo.androidMidiInfo,
            {
                inputPort = it.openInputPort(0)
                if (inputPort == null) {
                    this.midiDeviceInfo = null
                    toastManager.toast("Send: Device open but port closed")
                    return@openDevice
                }
                this.midiDeviceInfo = midiDeviceInfo
                toastManager.toast("Send: Device open and port open")
            },
            handler
        )
    }

    override fun send(
        @IntRange(from = 21, to = 108) midiCode: Int,
        @IntRange(from = 0, to = 127) velocity: Int
    ) {
        val buffer = ByteArray(32)
        var numBytes = 0
        val channel = 3 // MIDI channels 1-16 are encoded as 0-15.
        buffer[numBytes++] = (0x90 + (channel - 1)).toByte() // note on
        buffer[numBytes++] = midiCode.toByte() // pitch is middle C [21-108]
        buffer[numBytes++] = velocity.toByte() // max velocity
        val offset = 0
        // post is non-blocking
        inputPort?.send(buffer, offset, numBytes)
    }

    override fun close() {
        inputPort?.close()
        inputPort = null
        midiDeviceInfo = null
    }
}
