@file:Suppress("PackageName")

/* ktlint-disable package-name */
package com.mercandalli.android.sdk.midi.midi_view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mercandalli.android.sdk.midi.device_info.MidiDeviceInfo
import com.mercandalli.android.sdk.midi.midi_view_row.MidiRowView

class MidiViewAdapter(
    clickListener: MidiRowView.ClickListener? = null
) : ListDelegationAdapter<List<Any>>() {

    private val fileAdapterDelegate = MidiDeviceInfoAdapterDelegate(clickListener)

    init {
        delegatesManager.addDelegate(fileAdapterDelegate as AdapterDelegate<List<Any>>)
    }

    fun populate(list: List<MidiDeviceInfo>) {
        setItems(list)
        notifyDataSetChanged()
    }

    //region MidiDeviceInfo
    private class MidiDeviceInfoAdapterDelegate(
        private val clickListener: MidiRowView.ClickListener?
    ) : AbsListItemAdapterDelegate<Any, Any, MidiDeviceInfoRowHolder>() {

        private val rows = ArrayList<MidiRowView>()

        override fun isForViewType(o: Any, list: List<Any>, i: Int): Boolean {
            return o is MidiDeviceInfo
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup): MidiDeviceInfoRowHolder {
            val view = MidiRowView(viewGroup.context)
            view.layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            view.setClickListener(clickListener)
            rows.add(view)
            return MidiDeviceInfoRowHolder(view)
        }

        override fun onBindViewHolder(
            model: Any,
            playlistViewHolder: MidiDeviceInfoRowHolder,
            list: List<Any>
        ) {
            playlistViewHolder.bind(model as MidiDeviceInfo)
        }
    }

    private class MidiDeviceInfoRowHolder(
        private val view: MidiRowView
    ) : RecyclerView.ViewHolder(view) {
        fun bind(file: MidiDeviceInfo) {
            view.setMidiDeviceInfo(file)
        }
    }
    //endregion MidiDeviceInfo
}
