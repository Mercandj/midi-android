@file:Suppress("PackageName")

/* ktlint-disable package-name */
package com.mercandalli.android.sdk.midi.midi_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.mercandalli.android.apps.midi.R
import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfo
import com.mercandalli.android.sdk.midi.main.MidiGraph

class MidiView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr),
    MidiViewContract.Screen {

    private val view = View.inflate(context, R.layout.view_midi, this)
    private val list: TextView = view.findViewById(R.id.view_midi_list)
    private val send: View = view.findViewById(R.id.view_midi_send)
    private val listen: View = view.findViewById(R.id.view_midi_listen)
    private val userAction = createUserAction()

    init {
        send.setOnClickListener {
            userAction.onSendClicked()
        }
        listen.setOnClickListener {
            userAction.onListenClicked()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        userAction.onAttached()
    }

    override fun onDetachedFromWindow() {
        userAction.onDetached()
        super.onDetachedFromWindow()
    }

    override fun display(midiDeviceInfos: List<MidiDeviceInfo>) {
        list.text = midiDeviceInfos.joinToString("\n\n")
    }

    private fun createUserAction(): MidiViewContract.UserAction {
        if (isInEditMode) {
            return object : MidiViewContract.UserAction {
                override fun onAttached() {}
                override fun onDetached() {}
                override fun onSendClicked() {}
                override fun onListenClicked() {}
            }
        }
        val midiDeviceManager = MidiGraph.getMidiDeviceManager()
        val midiReceiver = MidiGraph.getMidiReceiver()
        val midiSender = MidiGraph.getMidiSender()
        val toastManager = MidiGraph.getToastManager()
        return MidiViewPresenter(
            this,
            midiDeviceManager,
            midiReceiver,
            midiSender,
            toastManager
        )
    }
}
