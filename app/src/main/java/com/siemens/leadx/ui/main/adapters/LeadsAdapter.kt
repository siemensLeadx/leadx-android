package com.siemens.leadx.ui.main.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.siemens.leadx.R
import com.siemens.leadx.data.remote.entites.Lead
import com.siemens.leadx.databinding.ItemLeadBinding
import com.siemens.leadx.databinding.ItemPagedListFooterBinding
import com.siemens.leadx.utils.RetryListener
import com.siemens.leadx.utils.base.BasePagedListAdapter
import com.siemens.leadx.utils.extensions.toDateWithMonthName2

class LeadsAdapter(
    private val retryListener: RetryListener,
    private val onItemClicked: (lead: Lead?) -> Unit,
) :
    BasePagedListAdapter<Lead>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE -> ItemViewHolder(
                ItemLeadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> FooterViewHolder(
                ItemPagedListFooterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                retryListener
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            getItemViewType(position) == FOOTER_VIEW_TYPE -> (holder as BasePagedListAdapter<*>.FooterViewHolder).setFooterView()
            else -> (holder as ItemViewHolder).bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getActualListItemCount() && getItem(position) != null)
            ITEM_VIEW_TYPE
        else
            FOOTER_VIEW_TYPE
    }

    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Lead> =
            object : DiffUtil.ItemCallback<Lead>() {
                override fun areItemsTheSame(
                    oldItem: Lead,
                    newItem: Lead,
                ): Boolean {
                    return oldItem.leadId == newItem.leadId
                }

                override fun areContentsTheSame(
                    oldItem: Lead,
                    newItem: Lead,
                ): Boolean {
                    return oldItem == newItem
                }
            }

        const val FOOTER_VIEW_TYPE = 1
        const val ITEM_VIEW_TYPE = 2
    }

    inner class ItemViewHolder(private val binding: ItemLeadBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Lead?) {
            with(binding) {
                tvLeadName.text = item?.leadName
                tvHospitalName.text = item?.hospitalName
                tvId.text = "${tvId.context.getString(R.string.id)} #${item?.leadId}"
                tvLeadStatus.text = item?.leadStatus
                tvDate.text = item?.createdOn?.times(1000)?.toDateWithMonthName2()
                try {
                    tvLeadStatus.setTextColor(Color.parseColor(item?.leadStatusTextColor))
                    tvLeadStatus.setBackgroundColor(Color.parseColor(item?.leadStatusBackColor))
                } catch (e: Exception) {
                    // pass
                }
                this.root.setOnClickListener {
                    onItemClicked.invoke(item)
                }
            }
        }
    }
}
