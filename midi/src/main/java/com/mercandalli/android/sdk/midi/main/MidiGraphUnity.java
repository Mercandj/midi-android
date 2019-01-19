package com.mercandalli.android.sdk.midi.main;

import android.content.Context;
import androidx.annotation.Keep;

@Keep
public class MidiGraphUnity {

    public static void initialize(Object context) {
        MidiGraph.initialize((Context) context);
    }


    public static void initialize(Context context) {
        MidiGraph.initialize(context);
    }

    public static void startMidiActivity() {
        MidiGraph.startMidiActivity();
    }
}
