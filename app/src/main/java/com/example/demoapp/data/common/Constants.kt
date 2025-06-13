package com.example.demoapp.data.common

import com.example.demoapp.BuildConfig

// Common Constants
const val DATABASE_NAME = "app-database"
const val DATE_STORE_NAME = "app-datastore"

object HttpClient {
    val READ_TIMEOUT = if (BuildConfig.DEBUG) 80 else 60
    val CALL_TIMEOUT = if (BuildConfig.DEBUG) 80 else 60
    val CONNECTION_TIMEOUT = if (BuildConfig.DEBUG) 80 else 60
}

object ErrorCode {
    const val BAD_REQUEST = 400
    const val NOT_FOUND = 404
}

object HeaderKeys {
    const val AUTH = "Authorization"
    const val BEARER = "Bearer "
}
