package com.example.myrealtripchallenge.repository

import com.example.myrealtripchallenge.dto.RssData
import io.reactivex.Single

interface RssRepository {
    fun requestData(hl:String, gl:String, ceid:String) : Single<RssData>
}