package com.siemens.leadx.utils

/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
sealed class PagedListFooterType {
    object Loading : PagedListFooterType()
    object Retry : PagedListFooterType()
    object None : PagedListFooterType()
}
