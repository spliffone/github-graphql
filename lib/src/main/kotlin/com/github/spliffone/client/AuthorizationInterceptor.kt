package com.github.spliffone.client

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val token: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", baererToken(token))
            .build()

        return chain.proceed(request)
    }

    fun baererToken(token: String): String {
        return "Baerer $token"
    }
}