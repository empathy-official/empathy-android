package com.empathy.empathy_android.ui.feedinput

import android.net.Uri
import com.empathy.empathy_android.repository.model.Feed

internal sealed class FeedInputLooknFeel {

    data class ShowFeedInputImage(val imageUri: Uri): FeedInputLooknFeel()

//    data class ShowInputInfo(): FeedInputLooknFeel()

}