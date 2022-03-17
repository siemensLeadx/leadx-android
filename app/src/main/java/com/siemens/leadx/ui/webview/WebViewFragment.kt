package com.siemens.leadx.ui.webview

import android.os.Bundle
import com.danjdt.pdfviewer.PdfViewer
import com.siemens.leadx.databinding.FragmentWebViewBinding
import com.siemens.leadx.utils.base.BaseFragment
import com.siemens.leadx.utils.base.BaseViewModel
import com.siemens.leadx.utils.extensions.webViewTitle
import com.siemens.leadx.utils.extensions.webViewUrl

class WebViewFragment : BaseFragment<FragmentWebViewBinding>(FragmentWebViewBinding::inflate) {
    private lateinit var title: String
    private lateinit var uri: String

    override fun getCurrentViewModel(): BaseViewModel? = null

    override fun onViewReady() {
        initArguments()
        initToolbar()
        initViews()
    }

    private fun initArguments() {
        arguments?.let {
            title = it.webViewTitle
            uri = it.webViewUrl
        }
    }

    private fun initToolbar() {
        with(binding.lToolbar) {
            tvTitle.text = title
            ivBack.setOnClickListener {
                activity?.finish()
            }
        }
    }

    private fun initViews() {
        with(binding) {
            PdfViewer.Builder(flPdf)
                .setZoomEnabled(true)
                .setMaxZoom(3f) //zoom multiplier
                .build()
                .load(uri)
        }
    }

    companion object {
        fun getInstance(bundle: Bundle?) = WebViewFragment().also {
            it.arguments = bundle
        }
    }
}
