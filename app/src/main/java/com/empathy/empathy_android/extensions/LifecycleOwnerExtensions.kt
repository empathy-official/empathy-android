package com.empathy.empathy_android.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.empathy.empathy_android.SimpleAction

internal fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, block: (T) -> Unit, onNullAction: SimpleAction? = null) = liveData.observe(this, Observer {
        it?.run {
            block(it)
        } ?: onNullAction?.invoke()
})