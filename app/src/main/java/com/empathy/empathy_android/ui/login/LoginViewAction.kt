package com.empathy.empathy_android.ui.login

import java.net.URL


internal sealed class LoginViewAction {

    data class OnLocationChange(
            val latitude: Double,
            val longtitude: Double

    ): LoginViewAction()

    data class LoginClick(val userId: String): LoginViewAction()
}