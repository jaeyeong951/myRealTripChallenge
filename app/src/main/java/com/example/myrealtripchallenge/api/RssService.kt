package com.example.myrealtripchallenge.api

import com.example.myrealtripchallenge.dto.RssData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RssService {
    @GET("/rss")
    fun requestData(
        @Query("hl") hl:String,
        @Query("gl") gl:String,
        @Query("ceid") ceid:String
    ):Single<RssData>
}