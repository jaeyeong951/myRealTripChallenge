package com.example.myrealtripchallenge.api

import io.reactivex.Single
import org.jsoup.nodes.Document
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface JsoupService {
    @GET()
    fun getResponse(@Url url:String): Single<String>
}