@file:Suppress("PackageName")

/* ktlint-disable package-name */
package com.mercandalli.android.sdk.midi.midi_view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
    private val log: TextView = view.findViewById(R.id.view_midi_log)
    private val recyclerView: RecyclerView = view.findViewById(R.id.view_midi_recycler_view)
    private val adapter = MidiViewAdapter()
    private val userAction = createUserAction()

    init {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
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
        adapter.populate(midiDeviceInfos)
    }

    override fun displayLog(text: String) {
        log.text = text
    }

    private fun createUserAction(): MidiViewContract.UserAction {
        if (isInEditMode) {
            return object : MidiViewContract.UserAction {
                override fun onAttached() {}
                override fun onDetached() {}
            }
        }
        val midiDeviceManager = MidiGraph.getMidiDeviceManager()
        val logManager = MidiGraph.getLogManager()
        return MidiViewPresenter(
            this,
            midiDeviceManager,
            logManager
        )
    }
}
