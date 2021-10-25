package com.siemens.leadx.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.siemens.leadx.data.local.entities.CreateLookUps
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.entites.Lead
import com.siemens.leadx.databinding.FragmentMainBinding
import com.siemens.leadx.ui.createlead.container.CreateLeadActivity
import com.siemens.leadx.ui.details.container.LeadDetailsActivity
import com.siemens.leadx.ui.main.adapters.LeadsAdapter
import com.siemens.leadx.ui.profile.container.ProfileActivity
import com.siemens.leadx.utils.PagedListFooterType
import com.siemens.leadx.utils.RetryListener
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseFragment
import com.siemens.leadx.utils.extensions.hideShimmerView
import com.siemens.leadx.utils.extensions.observe
import com.siemens.leadx.utils.extensions.showShimmerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private var adapter: LeadsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK)
                    viewModel.refresh(true)
            }
        super.onCreate(savedInstanceState)
    }

    override fun getCurrentViewModel() = viewModel

    override fun onViewReady() {
        initObservation()
        initViews()
        initClickListeners()
    }

    private fun initObservation() {
        observe(viewModel.createLookUpsStatus) {
            when (it) {
                is Status.Loading -> showDialogLoading()
                is Status.Success<BaseResponse<CreateLookUps>> ->
                    handleCreateLookupsSuccess(it.data?.data)
                is Status.Error ->
                    onError(it) {
                        hideDialogLoading()
                    }
            }
        }
        observe(viewModel.status) {
            when (it) {
                is Status.Loading -> handleLoading()
                is Status.LoadingMore -> handleLoadingMore()
                is Status.Success<BaseResponse<List<Lead>>> -> handleSuccess(it.data?.data)
                is Status.SuccessLoadingMore -> handleSuccessLoadingMore()
                is Status.Error<Any> -> onError(it) { handleError() }
                is Status.ErrorLoadingMore<Any> -> onError(it) { handleErrorLoadingMore() }
            }
        }
    }

    private fun initViews() {
        with(binding) {
            val user = viewModel.getUser()?.user
            lHeader.tvName.text = user?.getNameAsLetters()
            srlRefresh.setOnRefreshListener {
                srlRefresh.isRefreshing = false
                viewModel.refresh(false)
            }
            initPagedList()
        }
    }

    private fun initClickListeners() {
        with(binding) {
            lHeader.tvName.setOnClickListener {
                ProfileActivity.start(activity)
            }
            lCreateLead.cvCreateLead.setOnClickListener {
                viewModel.getCreateLookups()
            }
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
        adapter = LeadsAdapter(object : RetryListener {
            override fun onRetry() {
                viewModel.retry()
            }
        }) {
            LeadDetailsActivity.start(activity, it?.leadId ?: "")
        }
        binding.rvLeads.adapter = adapter
        binding.rvLeads.itemAnimator = null
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
            lShimmer.slLeads.showShimmerView()
            lNoData.llNoData.visibility = View.GONE
            rvLeads.visibility = View.GONE
        }
    }

    private fun handleError() {
        with(binding) {
            lShimmer.slLeads.hideShimmerView()
            lRetry.llError.visibility = View.VISIBLE
        }
    }

    private fun handleSuccess(data: List<Lead>?) {
        with(binding) {
            lShimmer.slLeads.hideShimmerView()
            if (data.isNullOrEmpty())
                lNoData.llNoData.visibility = View.VISIBLE
            else
                rvLeads.visibility = View.VISIBLE
        }
    }

    private fun handleCreateLookupsSuccess(createLookUps: CreateLookUps?) {
        hideDialogLoading()
        createLookUps?.let {
            CreateLeadActivity.start(this@MainFragment, activityResultLauncher, it)
        }
    }
}
