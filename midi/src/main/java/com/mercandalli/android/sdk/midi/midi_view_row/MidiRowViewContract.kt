@file:Suppress("PackageName")

/* ktlint-disable package-name */
package com.mercandalli.android.sdk.midi.midi_view_row

import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfo

interface MidiRowViewContract {

    interface UserAction {

        fun setMidiDeviceInfo(midiDeviceInfo: MidiDeviceInfo)

        fun onConnectClicked()

        fun onListenClicked()

        fun onSendClicked()

        fun onCloseClicked()

        fun onInputClicked(index: Int)
    }

    interface Screen {

        fun setName(text: String)

        fun setDescription(text: String)
    }
}
