package com.example.myrealtripchallenge.repository

import io.reactivex.Single

interface JsoupRepository {
    fun requestResponse(url : String) : Single<String>
}