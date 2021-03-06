@file:Suppress("PackageName")

/* ktlint-disable package-name */
package com.mercandalli.android.sdk.midi.midi_view

import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfo

interface MidiViewContract {

    interface UserAction {

        fun onAttached()

        fun onDetached()
    }

    interface Screen {

        fun display(midiDeviceInfos: List<MidiDeviceInfo>)

        fun displayLog(text: String)
    }
}
