package com.empathy.empathy_android.ui.myfeed

internal sealed class MyFeedViewAction {

    data class OnFeedDeleteClicked(val targetId: Int?, val adapterPosition: Int): MyFeedViewAction()

}
