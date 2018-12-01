package com.empathy.empathy_android.repository.model


internal data class User(
        val name: String = "",
        val loginApi: String = "facebook",
        val profileUrl: String = "",
        val appUserId: String = ""
)