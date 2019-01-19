package com.mercandalli.android.sdk.midi;

import android.media.midi.MidiDeviceInfo;
import android.media.midi.MidiDeviceStatus;
import android.media.midi.MidiManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.media.midi.MidiManager.DeviceCallback;
import android.os.Handler;

import java.util.HashMap;
import java.util.Map;

public class MidiDeviceMonitor {

    public final static String TAG = "MidiDeviceMonitor";

    private static MidiDeviceMonitor mInstance;
    private MidiManager mMidiManager;
    private HashMap<DeviceCallback, Handler> mCallbacks = new HashMap<DeviceCallback,Handler>();
    private MyDeviceCallback mMyDeviceCallback;
    // We only need the workaround for versions before N.
    private boolean mUseProxy = Build.VERSION.SDK_INT <= Build.VERSION_CODES.M;

    // Use an inner class so we do not clutter the API of MidiDeviceMonitor
    // with public DeviceCallback methods.
    protected class MyDeviceCallback extends DeviceCallback {

        @Override
        public void onDeviceAdded(final MidiDeviceInfo device) {
            // Call all of the locally registered callbacks.
            for(Map.Entry<DeviceCallback, Handler> item : mCallbacks.entrySet()) {
                final DeviceCallback callback = item.getKey();
                Handler handler = item.getValue();
                if(handler == null) {
                    callback.onDeviceAdded(device);
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onDeviceAdded(device);
                        }
                    });
                }
            }
        }

        @Override
        public void onDeviceRemoved(final MidiDeviceInfo device) {
            for(Map.Entry<DeviceCallback, Handler> item : mCallbacks.entrySet()) {
                final DeviceCallback callback = item.getKey();
                Handler handler = item.getValue();
                if(handler == null) {
                    callback.onDeviceRemoved(device);
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onDeviceRemoved(device);
                        }
                    });
                }
            }
        }

        @Override
        public void onDeviceStatusChanged(final MidiDeviceStatus status) {
            for(Map.Entry<DeviceCallback, Handler> item : mCallbacks.entrySet()) {
                final DeviceCallback callback = item.getKey();
                Handler handler = item.getValue();
                if(handler == null) {
                    callback.onDeviceStatusChanged(status);
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onDeviceStatusChanged(status);
                        }
                    });
                }
            }
        }
    }

    private MidiDeviceMonitor(MidiManager midiManager) {
        mMidiManager = midiManager;
        if (mUseProxy) {
            Log.i(TAG,"Running on M so we need to use the workaround.");
            mMyDeviceCallback = new MyDeviceCallback();
            mMidiManager.registerDeviceCallback(mMyDeviceCallback,
                    new Handler(Looper.getMainLooper()));
        }
    }

    public synchronized static MidiDeviceMonitor getInstance(MidiManager midiManager) {
        if (mInstance == null) {
            mInstance = new MidiDeviceMonitor(midiManager);
        }
        return mInstance;
    }

    public void registerDeviceCallback(DeviceCallback callback, Handler handler) {
        if (mUseProxy) {
            // Keep our own list of callbacks.
            mCallbacks.put(callback, handler);
        } else {
            mMidiManager.registerDeviceCallback(callback, handler);
        }
    }

    public void unregisterDeviceCallback(DeviceCallback callback) {
        if (mUseProxy) {
            mCallbacks.remove(callback);
        } else {
            // This works on N or later.
            mMidiManager.unregisterDeviceCallback(callback);
        }
    }
}