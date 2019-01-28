package com.mercandalli.android.sdk.midi.main;

import android.content.Context;
import android.support.annotation.Keep;
import com.mercandalli.android.sdk.midi.receiver.MidiReceiver;

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

    public static void registerMidiListener(final MidiListenerUnity listener) {
        MidiGraph.registerMidiListener(new MidiReceiver.MidiListener() {
            @Override
            public void onReceived(int midiCode, int velocity) {
                listener.onReceived(midiCode, velocity);
            }
        });
    }
}
