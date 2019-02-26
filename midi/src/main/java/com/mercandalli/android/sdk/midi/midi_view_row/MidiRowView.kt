@file:Suppress("PackageName")

/* ktlint-disable package-name */
package com.mercandalli.android.sdk.midi.midi_view_row

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.mercandalli.android.apps.midi.R
import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfo
import com.mercandalli.android.sdk.midi.main.MidiGraph

class MidiRowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr),
    MidiRowViewContract.Screen {

    private val view = View.inflate(context, R.layout.view_midi_row, this)
    private val name: TextView = view.findViewById(R.id.view_midi_row_name)
    private val description: TextView = view.findViewById(R.id.view_midi_row_description)
    private val connect: View = view.findViewById(R.id.view_midi_row_connect)
    private val send: View = view.findViewById(R.id.view_midi_row_send)
    private val close: View = view.findViewById(R.id.view_midi_row_close)
    private val listen: View = view.findViewById(R.id.view_midi_row_listen)
    private val input0: TextView = view.findViewById(R.id.view_midi_0)
    private val input1: TextView = view.findViewById(R.id.view_midi_1)
    private val input2: TextView = view.findViewById(R.id.view_midi_2)
    private val input3: TextView = view.findViewById(R.id.view_midi_3)
    private val input4: TextView = view.findViewById(R.id.view_midi_4)
    private val input5: TextView = view.findViewById(R.id.view_midi_5)
    private val input6: TextView = view.findViewById(R.id.view_midi_6)
    private val userAction = createUserAction()

    init {
        connect.setOnClickListener {
            userAction.onConnectClicked()
        }
        send.setOnClickListener {
            userAction.onSendClicked()
        }
        close.setOnClickListener {
            userAction.onCloseClicked()
        }
        listen.setOnClickListener {
            userAction.onListenClicked()
        }
        input0.setOnClickListener { userAction.onInputClicked(0) }
        input1.setOnClickListener { userAction.onInputClicked(1) }
        input2.setOnClickListener { userAction.onInputClicked(2) }
        input3.setOnClickListener { userAction.onInputClicked(3) }
        input4.setOnClickListener { userAction.onInputClicked(4) }
        input5.setOnClickListener { userAction.onInputClicked(5) }
        input6.setOnClickListener { userAction.onInputClicked(6) }
    }

    override fun setName(text: String) {
        name.text = text
    }

    override fun setDescription(text: String) {
        description.text = text
    }

    fun setClickListener(clickListener: ClickListener?) {

    }

    fun setMidiDeviceInfo(midiDeviceInfo: MidiDeviceInfo) {
        userAction.setMidiDeviceInfo(midiDeviceInfo)
    }

    private fun createUserAction(): MidiRowViewContract.UserAction {
        if (isInEditMode) {
            return object : MidiRowViewContract.UserAction {
                override fun setMidiDeviceInfo(midiDeviceInfo: MidiDeviceInfo) {}
                override fun onConnectClicked() {}
                override fun onSendClicked() {}
                override fun onCloseClicked() {}
                override fun onListenClicked() {}
                override fun onInputClicked(index: Int) {}
            }
        }
        val midiReceiver = MidiGraph.getMidiReceiver()
        val midiSender = MidiGraph.getMidiSender()
        return MidiRowViewPresenter(
            this,
            midiReceiver,
            midiSender
        )
    }

    interface ClickListener {

        fun onMidiRowClicked(midiDeviceInfo: MidiDeviceInfo)
    }
}
