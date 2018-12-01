package com.empathy.empathy_android.ui.feedinput

internal sealed class FeedInputViewAction {

    data class SaveFeed(val title: String, val description: String) : FeedInputViewAction()

}
