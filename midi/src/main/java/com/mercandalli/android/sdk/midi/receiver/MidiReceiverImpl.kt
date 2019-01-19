package com.mercandalli.android.sdk.midi.receiver

import android.media.midi.MidiManager
import android.media.midi.MidiOutputPort
import android.os.Handler
import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfo
import com.mercandalli.android.sdk.midi.toast.ToastManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MidiReceiverImpl(
    private val midiManager: MidiManager,
    private val handler: Handler,
    private val toastManager: ToastManager
) : MidiReceiver {

    private val midiListeners = ArrayList<MidiReceiver.MidiListener>()
    private var outputPort: MidiOutputPort? = null

    override fun listen(midiDeviceInfo: MidiDeviceInfo) {
        close()
        midiManager.openDevice(
            midiDeviceInfo.androidMidiInfo,
            {
                if (it == null) {
                    outputPort = null
                    toastManager.toast("Listen fail because midi device is null")
                    return@openDevice
                }
                outputPort = it.openOutputPort(0)
                if (outputPort == null) {
                    toastManager.toast("Listen: Device open but port closed")
                    return@openDevice
                } else {
                    toastManager.toast("Listen: Device open and port open")
                }
                outputPort?.connect(AndroidMidiReceiver())
            },
            handler
        )
    }

    override fun close() {
        outputPort?.close()
        outputPort = null
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

    inner class AndroidMidiReceiver : android.media.midi.MidiReceiver() {
        override fun onSend(msg: ByteArray?, offset: Int, count: Int, timestamp: Long) {
            msg?.let {
                val zero = it[0].toInt()
                val one = it[1].toInt()
                val midiCode = it[2].toInt()
                val three = it[3].toInt()
                toastManager.toast("Data received $zero $one $midiCode $three")
                GlobalScope.launch(Dispatchers.Main) {
                    for (listener in midiListeners) {
                        listener.onReceived(midiCode, three)
                    }
                }
            }
        }
    }
}
