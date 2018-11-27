package com.empathy.empathy_android.http.appchannel

import io.reactivex.Observable


internal interface AppChannelApi {
    fun ofData(): Observable<AppData>
    fun accept(data: AppData)
}