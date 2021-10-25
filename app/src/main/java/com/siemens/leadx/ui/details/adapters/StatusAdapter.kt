package com.siemens.leadx.ui.details.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.siemens.leadx.data.local.entities.LeadStatus
import com.siemens.leadx.databinding.ItemLeadStatusBinding
import com.siemens.leadx.utils.extensions.getColorCompat
import com.siemens.leadx.utils.extensions.getFormattedNumberAccordingToLocal

class StatusAdapter(private val items: List<LeadStatus>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            ItemLeadStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(items[position])
    }

    inner class ItemViewHolder(private val binding: ItemLeadStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LeadStatus) {
            with(binding) {
                tvTitle.text = tvTitle.context.getString(item.title)
                tvNumber.text = (adapterPosition + 1).getFormattedNumberAccordingToLocal()
                item.background?.let { tvNumber.setBackgroundResource(it) }
                item.numberColor?.let { tvNumber.setTextColor(tvNumber.context.getColorCompat(it)) }
                item.textColor?.let { tvTitle.setTextColor(tvTitle.context.getColorCompat(it)) }
                vLine2.visibility = View.VISIBLE
                vLine1.visibility = View.VISIBLE
                when (adapterPosition) {
                    0 -> vLine1.visibility = View.INVISIBLE
                    items.lastIndex -> vLine2.visibility = View.INVISIBLE
                }
            }
        }
    }
}
