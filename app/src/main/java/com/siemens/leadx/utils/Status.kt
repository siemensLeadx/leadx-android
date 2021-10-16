package com.siemens.leadx.utils

import com.siemens.leadx.data.remote.ErrorResponse

/**
 * Created by Norhan Elsawi on 5/10/2021.
 */
open class Status<out D, out E> {

    data class Success<D>(
        var data: D?,
    ) : Status<D, Nothing>()

    object SuccessLoadingMore : Status<Nothing, Nothing>()

    data class Error<E>(
        var errorResponse: ErrorResponse<E>,
    ) : Status<Nothing, E>()

    data class ErrorLoadingMore<E>(var errorResponse: ErrorResponse<E>) :
        Status<Nothing, E>()

    object Loading : Status<Nothing, Nothing>()

    object LoadingMore : Status<Nothing, Nothing>()
}
