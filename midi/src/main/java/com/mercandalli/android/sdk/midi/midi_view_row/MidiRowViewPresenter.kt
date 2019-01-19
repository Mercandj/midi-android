@file:Suppress("PackageName")

/* ktlint-disable package-name */
package com.mercandalli.android.sdk.midi.midi_view_row

import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfo
import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfoManager
import com.mercandalli.android.sdk.midi.log.LogManager
import com.mercandalli.android.sdk.midi.midi_view.MidiViewContract
import com.mercandalli.android.sdk.midi.receiver.MidiReceiver
import com.mercandalli.android.sdk.midi.sender.MidiSender
import com.mercandalli.android.sdk.midi.toast.ToastManager

class MidiRowViewPresenter(
    private val screen: MidiRowViewContract.Screen,
    private val midiReceiver: MidiReceiver,
    private val midiSender: MidiSender
) : MidiRowViewContract.UserAction {

    private var send: MidiSender.Sender? = null
    private var midiDeviceInfo: MidiDeviceInfo? = null

    override fun setMidiDeviceInfo(midiDeviceInfo: MidiDeviceInfo) {
        this.midiDeviceInfo = midiDeviceInfo
        val name = midiDeviceInfo.name
        val description = midiDeviceInfo.toString()
        screen.setName(name)
        screen.setDescription(description)
    }

    override fun onSendOneTimeClicked() {
        midiSender.send(midiDeviceInfo!!)
    }

    override fun onOpenClicked() {
        send = midiSender.send(
            midiDeviceInfo!!,
            60,
            127,
            false
        )
    }

    override fun onSendClicked() {
        send?.send()
    }

    override fun onCloseClicked() {
        send?.close()
    }

    override fun onListenClicked() {
        midiReceiver.listen(midiDeviceInfo!!)
    }

}
