package com.empathy.empathy_android.ui.login

import com.empathy.empathy_android.repository.model.LocalUser
import com.empathy.empathy_android.repository.model.User

internal sealed class LoginNavigation {

    data class LoginSuccess(val user: LocalUser): LoginNavigation()

}
