package com.mercandalli.android.sdk.midi.receiver

import android.media.midi.MidiManager
import android.os.Handler
import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfo
import com.mercandalli.android.sdk.midi.toast.ToastManager

class MidiReceiverImpl(
    private val midiManager: MidiManager,
    private val handler: Handler,
    private val toastManager: ToastManager
) : MidiReceiver {

    private val midiListeners = ArrayList<MidiReceiver.MidiListener>()

    override fun listen(midiDeviceInfo: MidiDeviceInfo) {
        midiManager.openDevice(
            midiDeviceInfo.androidMidiInfo,
            {
                val outputPort = it.openOutputPort(0)
                if (outputPort == null) {
                    toastManager.toast("Device open but port closed")
                    return@openDevice
                } else {
                    toastManager.toast("Device open and port open")
                }
                outputPort.connect(AndroidMidiReceiver())
            },
            handler
        )
    }

    inner class AndroidMidiReceiver : android.media.midi.MidiReceiver() {
        override fun onSend(msg: ByteArray?, offset: Int, count: Int, timestamp: Long) {
            toastManager.toast("Data received")
        }
    }

    override fun registerMidiListener(listener: MidiReceiver.MidiListener) {
        if (midiListeners.contains(listener)) {
            return
        }
        midiListeners.add(listener)
    }

    override fun unregisterMidiListener(listener: MidiReceiver.MidiListener) {
        midiListeners.remove(listener)
    }

}
