package com.example.myrealtripchallenge.di

import com.example.myrealtripchallenge.api.JsoupService
import com.example.myrealtripchallenge.api.RssService
import com.example.myrealtripchallenge.repository.JsoupRepository
import com.example.myrealtripchallenge.repository.JsoupRepositoryImpl
import com.example.myrealtripchallenge.repository.RssRepository
import com.example.myrealtripchallenge.repository.RssRepositoryImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.net.URLDecoder


val apiModule = module {
    factory { provideOkHttpClient()}
    factory { provideRssApi(provideRssRetrofit(get())) }
    factory { provideJsoupApi(provideJsoupRetrofit(get()))}

    single<RssRepository> { RssRepositoryImpl(get()) }
    single<JsoupRepository> { JsoupRepositoryImpl(get()) }
}

fun provideRssApi(retrofit: Retrofit):RssService = retrofit.create(RssService::class.java)
fun provideJsoupApi(retrofit: Retrofit):JsoupService = retrofit.create(JsoupService::class.java)

fun provideRssRetrofit(okHttpClient: OkHttpClient): Retrofit{
    return Retrofit.Builder().baseUrl("https://news.google.com").client(okHttpClient)
        .addConverterFactory(SimpleXmlConverterFactory.create()).addCallAdapterFactory(
            RxJava2CallAdapterFactory.create()).build()
}

fun provideJsoupRetrofit(okHttpClient: OkHttpClient): Retrofit{
    return Retrofit.Builder().baseUrl("https://news.google.com").client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create()).addCallAdapterFactory(
            RxJava2CallAdapterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    val httpClientBuilder = OkHttpClient().newBuilder()
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    httpClientBuilder.addInterceptor(logging)

    return httpClientBuilder.build()
}
