@file:Suppress("PackageName")

/* ktlint-disable package-name */
package com.mercandalli.android.sdk.midi.midi_view

import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfo
import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfoManager
import com.mercandalli.android.sdk.midi.receiver.MidiReceiver
import com.mercandalli.android.sdk.midi.sender.MidiSender
import com.mercandalli.android.sdk.midi.toast.ToastManager

class MidiViewPresenter(
    private val screen: MidiViewContract.Screen,
    private val midiDeviceInfoManager: MidiDeviceInfoManager,
    private val midiReceiver: MidiReceiver,
    private val midiSender: MidiSender,
    private val toastManager: ToastManager
) : MidiViewContract.UserAction {

    // Send from nexus 6 to pixel 3

    private val midiDevicesChangedListener = createMidiDevicesChangedListener()

    override fun onAttached() {
        midiDeviceInfoManager.registerMidiInfosChangedListener(midiDevicesChangedListener)
        syncDevices()
    }

    override fun onDetached() {
        midiDeviceInfoManager.unregisterMidiInfosChangedListener(midiDevicesChangedListener)
    }

    override fun onListenClicked() {
        val midiInfo = extractNexusDeviceInfo() ?: return
        midiReceiver.listen(midiInfo)
    }

    override fun onSendClicked() {
        val midiInfo = extractPixelDeviceInfo() ?: return
        midiSender.send(midiInfo)
    }

    private fun extractPixelDeviceInfo(): MidiDeviceInfo? {
        val midiInfos = midiDeviceInfoManager.getMidiInfos()
        for (midiInfo in midiInfos) {
            if (midiInfo.manufacturer.toLowerCase().contains("google")) {
                return midiInfo
            }
        }
        toastManager.toast("Device google not found")
        return null
    }

    private fun extractNexusDeviceInfo(): MidiDeviceInfo? {
        val midiInfos = midiDeviceInfoManager.getMidiInfos()
        for (midiInfo in midiInfos) {
            if (midiInfo.manufacturer.toLowerCase().contains("android")) {
                return midiInfo
            }
        }
        toastManager.toast("Device google not found")
        return null
    }

    private fun syncDevices() {
        val midiDevices = midiDeviceInfoManager.getMidiInfos()
        screen.display(midiDevices)
    }

    private fun createMidiDevicesChangedListener() = object : MidiDeviceInfoManager.MidiInfosChangedListener {
        override fun onMidiDevicesChanged() {
            syncDevices()
        }
    }
}
