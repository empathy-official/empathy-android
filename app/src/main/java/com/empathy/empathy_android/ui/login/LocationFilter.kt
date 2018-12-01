package com.empathy.empathy_android.ui.login

import com.empathy.empathy_android.repository.model.LocationEnum
import javax.inject.Inject

internal class LocationFilter @Inject constructor(): LocationFilterApi {

    private var address: String? = null

    override fun setLocationAddress(address: String) {
        this.address = address
    }

    override fun getLocationEnum(): LocationEnum? = address?.let {
        LocationEnum.valueOf(it)
    }

}
