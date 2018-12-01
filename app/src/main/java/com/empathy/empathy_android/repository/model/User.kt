package com.empathy.empathy_android.repository.model

import com.empathy.empathy_android.Constants


internal data class User(
        val name: String = "",
        val loginApi: String = "facebook",
        val profileUrl: String = "",
        val token: String = ""
)