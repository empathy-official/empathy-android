package com.empathy.empathy_android.ui.feed

internal sealed class FeedViewAction {

    object NavigateToMyFeedClicked: FeedViewAction()

    object NavigateToPartnerInfoClicked: FeedViewAction()

    object NavigateToTmapClicked: FeedViewAction()

    data class OnLocationChange(
            val latitude: Double,
            val longtitude: Double

    ): FeedViewAction()
}