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
    private val sendOneTime: View = view.findViewById(R.id.view_midi_row_send_one_time)
    private val open: View = view.findViewById(R.id.view_midi_row_open)
    private val send: View = view.findViewById(R.id.view_midi_row_send)
    private val close: View = view.findViewById(R.id.view_midi_row_close)
    private val listen: View = view.findViewById(R.id.view_midi_row_listen)
    private val userAction = createUserAction()

    init {
        sendOneTime.setOnClickListener {
            userAction.onSendOneTimeClicked()
        }
        open.setOnClickListener {
            userAction.onOpenClicked()
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
                override fun onSendOneTimeClicked() {}
                override fun onOpenClicked() {}
                override fun onSendClicked() {}
                override fun onCloseClicked() {}
                override fun onListenClicked() {}
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
