@file:Suppress("PackageName")

/* ktlint-disable package-name */
package com.mercandalli.android.sdk.midi.device_info

data class MidiDeviceInfo(
    val id: Int,
    val serialNumber: String?,
    val name: String,
    val manufacturer: String,
    val product: String,
    val version: String?,
    val androidMidiInfo: android.media.midi.MidiDeviceInfo
) {

    companion object {

        fun create(
            midiDeviceDeviceInfos: List<android.media.midi.MidiDeviceInfo>
        ): List<MidiDeviceInfo> {
            val midiDevices = ArrayList<MidiDeviceInfo>()
            for (midiDeviceInfo in midiDeviceDeviceInfos) {
                val midiDevice = create(midiDeviceInfo)
                midiDevices.add(midiDevice)
            }
            return midiDevices
        }

        private fun create(
            midiDeviceDeviceInfo: android.media.midi.MidiDeviceInfo
        ): MidiDeviceInfo {
            val id = midiDeviceDeviceInfo.id
            val properties = midiDeviceDeviceInfo.properties
            val serialNumber = properties.getString(android.media.midi.MidiDeviceInfo.PROPERTY_SERIAL_NUMBER)
            val name = properties.getString(android.media.midi.MidiDeviceInfo.PROPERTY_NAME)!!
            val manufacturer = properties.getString(android.media.midi.MidiDeviceInfo.PROPERTY_MANUFACTURER)!!
            val product = properties.getString(android.media.midi.MidiDeviceInfo.PROPERTY_PRODUCT)!!
            val version = properties.getString(android.media.midi.MidiDeviceInfo.PROPERTY_VERSION)
            return MidiDeviceInfo(
                id,
                serialNumber,
                name,
                manufacturer,
                product,
                version,
                midiDeviceDeviceInfo
            )
        }
    }
}
