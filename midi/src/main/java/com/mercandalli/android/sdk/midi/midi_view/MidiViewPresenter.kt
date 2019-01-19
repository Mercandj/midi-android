@file:Suppress("PackageName")

/* ktlint-disable package-name */
package com.mercandalli.android.sdk.midi.midi_view

import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfo
import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfoManager
import com.mercandalli.android.sdk.midi.log.LogManager
import com.mercandalli.android.sdk.midi.receiver.MidiReceiver
import com.mercandalli.android.sdk.midi.sender.MidiSender
import com.mercandalli.android.sdk.midi.toast.ToastManager
import java.lang.StringBuilder

class MidiViewPresenter(
    private val screen: MidiViewContract.Screen,
    private val midiDeviceInfoManager: MidiDeviceInfoManager,
    private val logManager: LogManager
) : MidiViewContract.UserAction {

    // Send from nexus 6 to pixel 3

    private val midiDevicesChangedListener = createMidiDevicesChangedListener()
    private val logListener = createLogListener()

    override fun onAttached() {
        midiDeviceInfoManager.registerMidiInfosChangedListener(midiDevicesChangedListener)
        syncDevices()
        logManager.registerLogListener(logListener)
        syncLog()
    }

    override fun onDetached() {
        midiDeviceInfoManager.unregisterMidiInfosChangedListener(midiDevicesChangedListener)
        logManager.unregisterLogListener(logListener)
    }

    private fun syncDevices() {
        val midiDevices = midiDeviceInfoManager.getMidiInfos()
        screen.display(midiDevices)
    }

    private fun syncLog() {
        val log = logManager.getLog().reversed()
        val logBuilder = StringBuilder()
        for (i in 0 until log.size) {
            logBuilder.append(log.size - i).append("\t\t").append(log[i]).append("\n")
        }
        screen.displayLog(logBuilder.toString())
    }

    private fun createMidiDevicesChangedListener() = object : MidiDeviceInfoManager.MidiInfosChangedListener {
        override fun onMidiDevicesChanged() {
            syncDevices()
        }
    }

    private fun createLogListener() = object : LogManager.LogListener {
        override fun onLogChanged() {
            syncLog()
        }
    }
}
