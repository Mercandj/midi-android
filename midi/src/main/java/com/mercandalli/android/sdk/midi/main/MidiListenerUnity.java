package com.mercandalli.android.sdk.midi.main;

public interface MidiListenerUnity {

    void onReceived(
            int midiCode,
            int velocity
    );
}
