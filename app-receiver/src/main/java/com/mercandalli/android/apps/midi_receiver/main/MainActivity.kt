package com.mercandalli.android.apps.midi_receiver.main

import android.annotation.SuppressLint
import android.media.midi.MidiManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mercandalli.android.apps.midi_receiver.R
import com.mercandalli.android.apps.midi_receiver.activity.ActivityExtension.bind
import android.media.midi.MidiReceiver
import java.io.IOException
import com.mercandalli.android.sdk.midi.MidiPortWrapper
import com.mercandalli.android.apps.midi_receiver.MidiOutputPortSelector
import com.mercandalli.android.sdk.midi.*

class MainActivity : AppCompatActivity() {

    private val output by bind<TextView>(R.id.activity_main_output)

    private lateinit var connectFramer: MidiFramer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val midiManager = getSystemService(MIDI_SERVICE) as MidiManager
        val loggingReceiver = LoggingReceiver(ScopeLogger { text ->
            text?.let {
                logInternal(it)
            }
        })
        connectFramer = MidiFramer(loggingReceiver)

        val logSenderSelector = object : MidiOutputPortSelector(
            midiManager,
            this,
            R.id.spinner_senders
        ) {
            override fun onPortSelected(wrapper: MidiPortWrapper?) {
                super.onPortSelected(wrapper)
                if (wrapper != null) {
                    logInternal(MidiPrinter.formatDeviceInfo(wrapper.deviceInfo))
                }
            }
        }

        val directReceiver = MyDirectReceiver()
        logSenderSelector.sender.connect(directReceiver)


        MidiScope.setScopeLogger(object :
            ScopeLogger {
            override fun log(text: String?) {
                text?.let {
                    logInternal(it)
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun logInternal(text: String) {
        output.text = "${output.text}\n$text"
    }

    private fun logByteArray(prefix: String, value: ByteArray, offset: Int, count: Int) {
        val builder = StringBuilder(prefix)
        for (i in 0 until count) {
            builder.append(String.format("0x%02X", value[offset + i]))
            if (i != count - 1) {
                builder.append(", ")
            }
        }
        logInternal(builder.toString())
    }

    internal inner class MyDirectReceiver : MidiReceiver() {
        @Throws(IOException::class)
        override fun onSend(
            data: ByteArray, offset: Int, count: Int,
            timestamp: Long
        ) {
            if (true) {
                val prefix = String.format("0x%08X, ", timestamp)
                logByteArray(prefix, data, offset, count)
            }
            // Send raw data to be parsed into discrete messages.
            connectFramer.send(data, offset, count, timestamp)
        }
    }
}
