package com.empathy.empathy_android.ui.myfeed

import com.empathy.empathy_android.repository.model.MyFeed

internal sealed class MyFeedLooknFeel {

    data class ShowMyFeeds(val myFeeds: MutableList<MyFeed>): MyFeedLooknFeel()

    data class DeleteMyFeed(val deletePosition: Int): MyFeedLooknFeel()

    object ShowEmptyFeeds: MyFeedLooknFeel()

}
