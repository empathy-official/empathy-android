package com.empathy.empathy_android.extensions

import io.reactivex.Observable
import io.reactivex.Single


fun <T : Any> Single<T>.toErrorSwallowingObservable(onError: (Throwable) -> Unit = {}): Observable<T> {
    return toObservable().onErrorResumeNext { throwable: Throwable ->
        onError(throwable)

        Observable.empty()
    }
}