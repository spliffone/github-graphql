package com.github.spliffone.client

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient

private var instance: ApolloClient? = null
private var endpoint: String = "https://api.github.com/graphql"

fun apolloClient(token: String): ApolloClient {
    if (instance != null) {
        return instance!!
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationInterceptor(token))
        .build()

    instance = ApolloClient.Builder()
        .serverUrl(endpoint)
        .okHttpClient(okHttpClient)
        .build()

    return instance!!
}

