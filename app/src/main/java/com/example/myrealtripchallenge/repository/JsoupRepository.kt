package com.example.myrealtripchallenge.repository

import io.reactivex.Single
import org.jsoup.nodes.Document

interface JsoupRepository {
    fun requestResponse(url : String) : Single<String>
}