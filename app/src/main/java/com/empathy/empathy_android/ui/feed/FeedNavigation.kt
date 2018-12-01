package com.empathy.empathy_android.ui.feed

import com.empathy.empathy_android.repository.model.LocalUser

internal sealed class FeedNavigation {

    data class NavigateToFeed(val user: LocalUser): FeedNavigation()

    data class NavigateToPartnerInfo(val user: LocalUser): FeedNavigation()


}