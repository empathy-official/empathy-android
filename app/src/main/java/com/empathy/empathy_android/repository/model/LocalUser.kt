package com.empathy.empathy_android.repository.model

import com.empathy.empathy_android.Constants
import java.io.Serializable


data class LocalUser(
        val name: String = "",
        val profileUrl: String = "",
        val address: String = "",
        val userId: Long = 1,
        var userLocationEnum: LocationEnum = LocationEnum.Seoul,
        var latitude: Double = Constants.DEFAULT_LATITUDE,
        var longtitude: Double = Constants.DEFAULT_LONGTITUDE

): Serializable