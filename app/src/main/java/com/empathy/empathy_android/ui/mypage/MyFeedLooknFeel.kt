package com.empathy.empathy_android.ui.mypage

import com.empathy.empathy_android.repository.model.MyFeed

internal sealed class MyFeedLooknFeel {

    data class ShowMyFeeds(val myFeeds: MutableList<MyFeed>): MyFeedLooknFeel()

    object ShowEmptyFeeds: MyFeedLooknFeel()

}
