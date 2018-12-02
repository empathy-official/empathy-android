package com.empathy.empathy_android.ui.myfeed

import com.empathy.empathy_android.repository.model.LocalUser

internal sealed class MyFeedNavigation {

    data class NavigateToFeedInput(
            val imageUri: String,
            val user: LocalUser

    ): MyFeedNavigation()

}
