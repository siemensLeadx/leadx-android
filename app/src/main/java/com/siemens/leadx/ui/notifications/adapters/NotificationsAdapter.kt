package com.siemens.leadx.ui.notifications.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.siemens.leadx.R
import com.siemens.leadx.data.local.entities.LeadStatusType.ORDERED
import com.siemens.leadx.data.local.entities.LeadStatusType.PROMOTED
import com.siemens.leadx.data.remote.entites.Notification
import com.siemens.leadx.databinding.ItemNotificationBinding
import com.siemens.leadx.databinding.ItemPagedListFooterBinding
import com.siemens.leadx.utils.RetryListener
import com.siemens.leadx.utils.base.BasePagedListAdapter
import com.siemens.leadx.utils.extensions.toDate2

class NotificationsAdapter(
    private val retryListener: RetryListener,
    private val onItemClicked: (notification: Notification?) -> Unit,
) :
    BasePagedListAdapter<Notification>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE -> ItemViewHolder(
                ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Notification> =
            object : DiffUtil.ItemCallback<Notification>() {
                override fun areItemsTheSame(
                    oldItem: Notification,
                    newItem: Notification,
                ): Boolean {
                    return oldItem.sentOn == newItem.sentOn
                }

                override fun areContentsTheSame(
                    oldItem: Notification,
                    newItem: Notification,
                ): Boolean {
                    return oldItem == newItem
                }
            }

        const val FOOTER_VIEW_TYPE = 1
        const val ITEM_VIEW_TYPE = 2
    }

    inner class ItemViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Notification?) {
            with(binding) {
                tvMsg.text = item?.message
                tvDate.text = item?.sentOn?.times(1000)?.toDate2()
                if (arrayOf(
                        PROMOTED,
                        ORDERED
                    ).any { item?.leadStatusId == it }
                )
                    setLeadStatus(
                        R.drawable.bg_circle_primary_color,
                        R.drawable.ic_rewarded,
                        View.VISIBLE,
                        binding
                    )
                else
                    setLeadStatus(
                        R.drawable.bg_circle_gray,
                        R.drawable.ic_notification,
                        View.INVISIBLE,
                        binding
                    )
                binding.root.setOnClickListener {
                    onItemClicked.invoke(item)
                }
            }
        }

        private fun setLeadStatus(
            @DrawableRes background: Int,
            @DrawableRes imgResource: Int,
            visibility: Int,
            binding: ItemNotificationBinding,
        ) {
            with(binding) {
                ivImage.setBackgroundResource(background)
                ivImage.setImageResource(imgResource)
                tvRewarded.visibility = visibility
            }
        }
    }
}
