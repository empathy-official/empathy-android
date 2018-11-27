package com.empathy.empathy_android.ui.login


internal sealed class LoginViewAction {

    data class OnLocationChange(
            val latitude: Double,
            val longtitude: Double
    ): LoginViewAction()
}