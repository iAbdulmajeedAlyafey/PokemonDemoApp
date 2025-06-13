package com.example.demoapp.data.common.source.remote

import com.example.demoapp.data.common.HeaderKeys
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
  // Lazy injection here for Auth Repository
) : Interceptor {

  @Throws(IOException::class)
  override fun intercept(chain : Interceptor.Chain) : Response {
    val token = runBlocking {
      // Here we should get token from auth repo.
      "66rewr8ew67894t34236423498jtyZ232"
    }
    val request = chain.request().newBuilder()
      .header(HeaderKeys.AUTH, HeaderKeys.BEARER.plus(token))
      .build()
    return chain.proceed(request)
  }
}