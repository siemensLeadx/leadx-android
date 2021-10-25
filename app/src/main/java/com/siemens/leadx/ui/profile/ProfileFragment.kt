package com.siemens.leadx.ui.profile

import androidx.fragment.app.viewModels
import com.siemens.leadx.R
import com.siemens.leadx.databinding.FragmentProfileBinding
import com.siemens.leadx.ui.main.container.MainActivity
import com.siemens.leadx.utils.base.BaseFragment
import com.siemens.leadx.utils.extensions.showLanguageDialog
import com.siemens.leadx.utils.extensions.showLogOutDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel by viewModels<ProfileViewModel>()

    override fun getCurrentViewModel() = viewModel

    override fun onViewReady() {
        initToolbar()
        initViews()
        initClickListeners()
    }

    private fun initToolbar() {
        with(binding.lToolbar) {
            tvTitle.text = getString(R.string.profile)
            ivBack.setOnClickListener {
                activity?.finish()
            }
        }
    }

    private fun initViews() {
        with(binding) {
            val user = viewModel.getUser()?.user
            tvName.text = user?.getNameAsLetters()
            tvEmail.text = user?.email
        }
    }

    private fun initClickListeners() {
        with(binding) {
            lChangeLanguage.cvChangeLanguage.setOnClickListener {
                context?.showLanguageDialog(viewModel.getCurrentLanguage()) {
                    viewModel.setLanguage(it)
                    restart()
                }
            }
            lLogout.cvLogout.setOnClickListener {
                context?.showLogOutDialog {
                }
            }
        }
    }

    // finish all opened activities first then open new one with updated config
    private fun restart() {
        activity?.finishAffinity()
        MainActivity.start(activity, finish = false)
    }
}
