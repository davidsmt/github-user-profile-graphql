package com.githubuserprofile.data.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class ApolloManager {

    private val _client: ApolloClient = buildApolloClient()
    val client: ApolloClient = _client

    private fun buildApolloClient(): ApolloClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(AuthorizationInterceptor())
            .build()

        val cacheFactory = LruNormalizedCacheFactory(
            EvictionPolicy.builder()
                .maxSizeBytes(MAX_SIZE_BYTES)
                .build()
        )

        return ApolloClient.builder()
            .serverUrl(BASE_SERVER_URL)
            .defaultHttpCachePolicy(
                HttpCachePolicy.CACHE_FIRST.expireAfter(CACHE_TIMEOUT_IN_DAYS, TimeUnit.DAYS)
            )
            .normalizedCache(cacheFactory)
            .subscriptionTransportFactory(
                WebSocketSubscriptionTransport.Factory(
                    WEBSOCKET_URL,
                    okHttpClient
                )
            )
            .okHttpClient(okHttpClient)
            .build()
    }

    private class AuthorizationInterceptor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "bearer $TOKEN")
                .build()

            return chain.proceed(request)
        }
    }

    companion object {
        private const val CACHE_TIMEOUT_IN_DAYS = 1L // 1 Day cache
        private const val MAX_SIZE_BYTES = 10L * 1024 * 1024 // 10MB Size
        private const val WEBSOCKET_URL = "wss://apollo-fullstack-tutorial.herokuapp.com/graphql"
        private const val BASE_SERVER_URL = "https://api.github.com/graphql"

        // User your own GitHub personal token here
        private const val TOKEN = "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"
    }

}