package com.empathy.empathy_android.ui.feeddetail

import com.empathy.empathy_android.repository.model.FeedDetail


internal sealed class FeedDetailLooknFeel {

    data class ShowFeedDetail(val feedDetail: FeedDetail): FeedDetailLooknFeel()

    data class ShowEditOrShareImage(val imageResource: Int): FeedDetailLooknFeel()
}