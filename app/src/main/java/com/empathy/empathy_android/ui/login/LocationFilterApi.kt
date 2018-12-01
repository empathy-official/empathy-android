package com.empathy.empathy_android.ui.login

import com.empathy.empathy_android.repository.model.LocationEnum


internal interface LocationFilterApi {
    fun setLocationAddress(address: String)
    fun getLocationEnum(): LocationEnum?
}