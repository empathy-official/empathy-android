package com.empathy.empathy_android.ui.feed

import com.empathy.empathy_android.repository.model.Feed

internal sealed class FeedLooknFeel {

    data class ShowFeeds(val feeds: MutableList<Feed>) : FeedLooknFeel()
}