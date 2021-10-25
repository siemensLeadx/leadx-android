package com.siemens.leadx.ui.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.siemens.leadx.R
import com.siemens.leadx.databinding.ItemDetailsDeviceBinding
import com.siemens.leadx.utils.extensions.getFormattedNumberAccordingToLocal

class DevicesAdapter(
    private val items: List<String>,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            ItemDetailsDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(items[position])
    }

    inner class ItemViewHolder(private val binding: ItemDetailsDeviceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            with(binding) {
                etDevice.setText(item)
                tilDevice.hint =
                    "${tilDevice.context.getString(R.string.device_need)} ${(adapterPosition + 1).getFormattedNumberAccordingToLocal()}"
            }
        }
    }
}
