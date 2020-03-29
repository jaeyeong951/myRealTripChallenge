package com.example.myrealtripchallenge.repository

import com.example.myrealtripchallenge.api.RssService
import com.example.myrealtripchallenge.dto.RssData
import io.reactivex.Single

class RssRepositoryImpl constructor(private val rssService: RssService) : RssRepository {
    override fun requestData(hl: String, gl: String, ceid: String): Single<RssData> {
        return rssService.requestData(hl, gl, ceid)
    }
}