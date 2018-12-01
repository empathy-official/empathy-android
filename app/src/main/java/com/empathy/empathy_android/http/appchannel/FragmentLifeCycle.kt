package com.empathy.empathy_android.http.appchannel

import android.content.Intent
import android.os.Bundle


internal sealed class FragmentLifeCycle {
    object OnAttach : FragmentLifeCycle()

    object OnCreate : FragmentLifeCycle()

    data class OnCreateView(val arguments: Bundle? = null, val savedInstanceState: Bundle? = null) : FragmentLifeCycle()

    data class OnViewCreated(val arguments: Bundle? = null, val savedInstanceState: Bundle? = null) : FragmentLifeCycle()

    data class OnActivityCreated(val savedInstanceState: Bundle? = null) : FragmentLifeCycle()

    object OnStart : FragmentLifeCycle()

    object OnResume : FragmentLifeCycle()

    object OnPause : FragmentLifeCycle()

    object OnStop : FragmentLifeCycle()

    object OnDestroyView : FragmentLifeCycle()

    object OnDestroy : FragmentLifeCycle()

    object OnDetach : FragmentLifeCycle()

    data class OnActivityResult(val requestCode: Int, val resultCode: Int, val data: Intent?) : FragmentLifeCycle()
}
