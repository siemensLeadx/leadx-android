package com.siemens.leadx.data.local.entities

sealed class FieldError {

    class NameError(val msg: String) : FieldError()

    class HospitalNameError(val msg: String) : FieldError()

    class RegionError(val msg: String) : FieldError()

    class CustomerStatusError(val msg: String) : FieldError()

    class DateError(val msg: String) : FieldError()

    class ContactPersonError(val msg: String) : FieldError()

    object None : FieldError()
}