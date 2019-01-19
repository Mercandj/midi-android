@file:Suppress("PackageName")

/* ktlint-disable package-name */
package com.mercandalli.android.sdk.midi.device_info

import android.media.midi.MidiManager
import android.os.Handler
import android.os.Looper

class MidiDeviceInfoManagerImpl(
    private val midiManager: MidiManager,
    private val handler: Handler
) : MidiDeviceInfoManager {

    private val midiInfosChangedListeners = ArrayList<MidiDeviceInfoManager.MidiInfosChangedListener>()

    init {
        val deviceCallback = object : MidiManager.DeviceCallback() {
            override fun onDeviceAdded(deviceInfo: android.media.midi.MidiDeviceInfo) {
                for (listener in midiInfosChangedListeners) {
                    listener.onMidiDevicesChanged()
                }
            }

            override fun onDeviceRemoved(deviceInfo: android.media.midi.MidiDeviceInfo) {
                for (listener in midiInfosChangedListeners) {
                    listener.onMidiDevicesChanged()
                }
            }
        }
        midiManager.registerDeviceCallback(deviceCallback, handler)
    }

    override fun getMidiInfos(): List<com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfo> {
        val devices = midiManager.devices
        return MidiDeviceInfo.create(devices.asList())
    }

    override fun registerMidiInfosChangedListener(listener: MidiDeviceInfoManager.MidiInfosChangedListener) {
        if (midiInfosChangedListeners.contains(listener)) {
            return
        }
        midiInfosChangedListeners.add(listener)
    }

    override fun unregisterMidiInfosChangedListener(listener: MidiDeviceInfoManager.MidiInfosChangedListener) {
        midiInfosChangedListeners.remove(listener)
    }

}
