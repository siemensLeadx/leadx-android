package com.siemens.leadx.ui.notifications

import android.view.View
import androidx.fragment.app.viewModels
import com.siemens.leadx.R
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.entites.Notification
import com.siemens.leadx.databinding.FragmentNotificationsBinding
import com.siemens.leadx.ui.details.container.LeadDetailsActivity
import com.siemens.leadx.ui.notifications.adapters.NotificationsAdapter
import com.siemens.leadx.utils.PagedListFooterType
import com.siemens.leadx.utils.RetryListener
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseFragment
import com.siemens.leadx.utils.extensions.hideShimmerView
import com.siemens.leadx.utils.extensions.observe
import com.siemens.leadx.utils.extensions.showShimmerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment :
    BaseFragment<FragmentNotificationsBinding>(FragmentNotificationsBinding::inflate) {

    private val viewModel by viewModels<NotificationsViewModel>()
    private var adapter: NotificationsAdapter? = null

    override fun getCurrentViewModel() = viewModel

    override fun onViewReady() {
        initToolbar()
        initObservation()
        initViews()
        initClickListeners()
    }

    private fun initToolbar() {
        with(binding.lToolbar) {
            tvTitle.text = getString(R.string.notifications)
            ivBack.setOnClickListener {
                activity?.finish()
            }
        }
    }

    private fun initObservation() {
        observe(viewModel.status) {
            when (it) {
                is Status.Loading -> handleLoading()
                is Status.LoadingMore -> handleLoadingMore()
                is Status.Success<BaseResponse<List<Notification>>> -> handleSuccess(it.data?.data)
                is Status.SuccessLoadingMore -> handleSuccessLoadingMore()
                is Status.Error<Any> -> onError(it) { handleError() }
                is Status.ErrorLoadingMore<Any> -> onError(it) { handleErrorLoadingMore() }
            }
        }
    }

    private fun initViews() {
        with(binding) {
            srlRefresh.setOnRefreshListener {
                srlRefresh.isRefreshing = false
                viewModel.refresh(false)
            }
            initPagedList()
        }
    }

    private fun initClickListeners() {
        with(binding) {
            lRetry.llRetryLoading.setOnClickListener {
                viewModel.retry()
            }
        }
    }

    private fun initPagedList() {
        initRecyclerView()
        viewModel.initPagedList()
        observe(viewModel.pagedList) {
            adapter?.submitList(it)
        }
    }

    private fun initRecyclerView() {
        adapter = NotificationsAdapter(object : RetryListener {
            override fun onRetry() {
                viewModel.retry()
            }
        }) { notification ->
            notification?.leadId?.let {
                LeadDetailsActivity.start(activity, it)
            }
        }
        binding.rvNotifications.adapter = adapter
        binding.rvNotifications.itemAnimator = null
    }

    private fun handleLoadingMore() {
        adapter?.setFooter(PagedListFooterType.Loading)
    }

    private fun handleSuccessLoadingMore() {
        adapter?.setFooter(PagedListFooterType.None)
    }

    private fun handleErrorLoadingMore() {
        adapter?.setFooter(PagedListFooterType.Retry)
    }

    private fun handleLoading() {
        with(binding) {
            lRetry.llError.visibility = View.GONE
            lShimmer.slNotifications.showShimmerView()
            lNoData.llNoData.visibility = View.GONE
            rvNotifications.visibility = View.GONE
        }
    }

    private fun handleError() {
        with(binding) {
            lShimmer.slNotifications.hideShimmerView()
            lRetry.llError.visibility = View.VISIBLE
        }
    }

    private fun handleSuccess(data: List<Notification>?) {
        with(binding) {
            lShimmer.slNotifications.hideShimmerView()
            if (data.isNullOrEmpty())
                lNoData.llNoData.visibility = View.VISIBLE
            else
                rvNotifications.visibility = View.VISIBLE
        }
    }
}
