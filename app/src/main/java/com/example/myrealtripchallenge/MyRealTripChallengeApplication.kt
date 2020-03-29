package com.example.myrealtripchallenge

import android.app.Application
import com.example.myrealtripchallenge.di.apiModule
import com.example.myrealtripchallenge.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyRealTripChallengeApplication : Application() {
    private val DiModule =listOf(apiModule, viewModelModule)

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyRealTripChallengeApplication)
            modules(DiModule)
        }
    }
}