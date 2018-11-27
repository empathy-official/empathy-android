package com.empathy.empathy_android.http.appchannel

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import javax.inject.Inject


internal class AppChannel @Inject constructor(): AppChannelApi {

    private val appDataChannel: Relay<AppData> = PublishRelay.create()

    override fun ofData(): Observable<AppData> = appDataChannel.toSerialized()

    override fun accept(data: AppData) = appDataChannel.toSerialized().accept(data)

}