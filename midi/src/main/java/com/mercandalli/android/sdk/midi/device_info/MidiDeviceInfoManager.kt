@file:Suppress("PackageName")

/* ktlint-disable package-name */
package com.mercandalli.android.sdk.midi.device_info

interface MidiDeviceInfoManager {

    fun getMidiInfos(): List<MidiDeviceInfo>

    fun registerMidiInfosChangedListener(listener: MidiInfosChangedListener)

    fun unregisterMidiInfosChangedListener(listener: MidiInfosChangedListener)

    interface MidiInfosChangedListener {

        fun onMidiDevicesChanged()
    }
}
