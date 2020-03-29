package com.example.myrealtripchallenge.repository

import com.example.myrealtripchallenge.api.JsoupService
import io.reactivex.Single

class JsoupRepositoryImpl constructor(private val jsoupService: JsoupService) : JsoupRepository{
    override fun requestResponse(url : String): Single<String> {
        return jsoupService.getResponse(url)
}
}