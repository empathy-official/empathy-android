package com.empathy.empathy_android.ui.feed

import com.empathy.empathy_android.repository.model.Feed

internal sealed class FeedLooknFeel {

    data class ShowFeeds(val feeds: MutableList<Feed>) : FeedLooknFeel()

    data class ShowFeedPlaceHolder(
            val content: String,
            val placeholderImage: String

    ): FeedLooknFeel()

    data class ShowMyFeed(
            val content: String,
            val myFeedImage: String

    ): FeedLooknFeel()

    data class ShowSubContent(val subContent: String): FeedLooknFeel()

    data class ShowLocation(val location: String): FeedLooknFeel()

    object CloseGps: FeedLooknFeel()

    object SetGps: FeedLooknFeel()

}