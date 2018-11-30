package com.empathy.empathy_android.repository.model

import java.io.Serializable


data class LocalUser(
        val name: String,
        val profileUrl: String,
        val address: String,
        val userId: Long

): Serializable