package com.empathy.empathy_android.data

import android.content.Intent
import android.os.Bundle

internal sealed class LifecycleState {
    data class OnCreate(val intent: Intent, val saveInstantState: Bundle?) : LifecycleState()

    object OnStart : LifecycleState()

    object OnResume : LifecycleState()

    object OnPause : LifecycleState()

    object OnStop : LifecycleState()

    object OnDestroy : LifecycleState()

    class OnActivityResult(val requestCode: Int, val resultCode: Int, val data: Intent?) : LifecycleState()
}