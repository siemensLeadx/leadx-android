package com.siemens.leadx.ui.createlead.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.siemens.leadx.data.local.entities.Device
import com.siemens.leadx.data.remote.entites.LookUp
import com.siemens.leadx.databinding.ItemDeviceBinding
import com.siemens.leadx.utils.extensions.showPopUpMenu

class DevicesAdapter(
    private val items: MutableList<Device>,
    private val devicesList: List<LookUp>?,
    private val onSameSelection: () -> Unit,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(items[position])
    }

    fun addDevice() {
        items.add(Device())
        notifyItemInserted(itemCount)
    }

    inner class ItemViewHolder(private val binding: ItemDeviceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Device) {
            with(binding) {
                etDevice.setText(item.lookUp?.name)
                if (adapterPosition == 0)
                    ivClear.visibility = View.GONE
                else
                    ivClear.visibility = View.VISIBLE
                ivClear.setOnClickListener {
                    items.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
                etDevice.setOnClickListener {
                    etDevice.showPopUpMenu(devicesList) { lookUp ->
                        val itemWithSameLookUp = items.firstOrNull { it.lookUp?.id == lookUp.id }
                        if (itemWithSameLookUp == null || itemWithSameLookUp == item) {
                            item.lookUp = lookUp
                            notifyItemChanged(adapterPosition)
                        } else
                            onSameSelection.invoke()

                    }
                }
            }
        }
    }
}