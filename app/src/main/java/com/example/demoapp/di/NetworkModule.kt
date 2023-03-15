package com.example.demoapp.di

import com.example.demoapp.BuildConfig
import com.example.demoapp.data.common.HttpClient
import com.example.demoapp.data.common.source.remote.AccessTokenInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.example.demoapp.di.annotations.MainClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @MainClient
    @Provides
    fun provideMainOkHttpClient(
        upstreamClient: OkHttpClient,
        accessTokenInterceptor: AccessTokenInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return getHttpClientBuilder(upstreamClient)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor(accessTokenInterceptor)
            .build()
    }

    @MainClient
    @Provides
    fun provideMainRetrofit(
        @MainClient okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
        @MainClient baseURL: String,
    ) = createRetrofit(
        okhttpClient,
        converterFactory,
        baseURL
    )

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .serializeNulls()
        .create()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    private fun getHttpClientBuilder(
        upstreamClient: OkHttpClient
    ): OkHttpClient.Builder = upstreamClient.newBuilder()
        .callTimeout(HttpClient.CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .readTimeout(HttpClient.READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)

    private fun createRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
        baseURL: String,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(converterFactory)
        .client(okhttpClient)
        .build()
}