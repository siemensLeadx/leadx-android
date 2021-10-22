package com.siemens.leadx.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.siemens.leadx.data.local.entities.CreateLookUps
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.databinding.FragmentMainBinding
import com.siemens.leadx.ui.createlead.container.CreateLeadActivity
import com.siemens.leadx.ui.profile.container.ProfileActivity
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseFragment
import com.siemens.leadx.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {

                }
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
    }

    private fun initViews() {
        with(binding) {
            val user = viewModel.getUser()?.user
            lHeader.tvName.text = user?.getNameAsLetters()
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
        }
    }

    private fun handleCreateLookupsSuccess(createLookUps: CreateLookUps?) {
        hideDialogLoading()
        createLookUps?.let {
            CreateLeadActivity.start(this@MainFragment, activityResultLauncher, it)
        }
    }
}
