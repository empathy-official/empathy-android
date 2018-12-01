package com.empathy.empathy_android.http.appchannel

import android.content.Intent
import android.os.Bundle

internal sealed class ActivityLifecycleState {
    data class OnCreate(val intent: Intent, val saveInstantState: Bundle?) : ActivityLifecycleState()

    object OnStart : ActivityLifecycleState()

    object OnResume : ActivityLifecycleState()

    object OnPause : ActivityLifecycleState()

    object OnStop : ActivityLifecycleState()

    object OnDestroy : ActivityLifecycleState()

    class OnActivityResult(val requestCode: Int, val resultCode: Int, val data: Intent?) : ActivityLifecycleState()
}