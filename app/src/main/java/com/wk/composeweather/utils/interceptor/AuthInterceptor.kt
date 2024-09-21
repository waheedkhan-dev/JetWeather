package com.wk.composeweather.utils.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

private const val TAG = "AuthInterceptor"
class AuthInterceptor @Inject constructor() :
    Interceptor {

    companion object {private const val TAG = "AuthInterceptor" }
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        Timber.tag(TAG).d("token = ")
        requestBuilder.addHeader("Authorization", "Bearer ")
        return chain.proceed(requestBuilder.build())
    }

}