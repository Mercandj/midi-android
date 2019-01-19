package com.mercandalli.android.apps.midi.main

import android.os.Bundle
import android.system.Os
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mercandalli.android.apps.midi.R
import com.mercandalli.android.apps.midi.activity.ActivityExtension.bind
import com.mercandalli.android.sdk.midi.main.MidiGraph

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
