package com.empathy.empathy_android

import android.content.Intent
import androidx.annotation.CallSuper
import androidx.annotation.CheckResult
import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

typealias RequestCode = Int
typealias ResultCode = Int

internal abstract class BaseViewModel: ViewModel() {

    private val intentRelay = PublishRelay.create<Intent>()

    private val activityResultRelay = PublishRelay.create<Triple<Int, Int, Intent?>>()

    protected val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCleared() {
        compositeDisposable.dispose()

        super.onCleared()
    }

    @CallSuper
    fun intent(intent: Intent) {
        intentRelay.accept(intent)
    }

    @CheckResult
    fun intent(): Observable<Intent> = intentRelay

    fun onActivityResult(requestCode: RequestCode, resultCode: ResultCode, data: Intent?) {
        activityResultRelay.accept(Triple(requestCode, resultCode, data))
    }

    @CheckResult
    fun activityResult(): Observable<Triple<RequestCode, ResultCode, Intent?>> = activityResultRelay



    fun addDisposables(vararg disposables: Disposable) {
        compositeDisposable.addAll(*disposables)
    }
}
