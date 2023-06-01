package com.yara.kinoapp

import android.app.Application
import com.yara.kinoapp.data.ApiConstants
import com.yara.kinoapp.data.MainRepository
import com.yara.kinoapp.data.OmdbApi
import com.yara.kinoapp.domain.Interactor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App : Application() {
    lateinit var repo: MainRepository
    lateinit var interactor: Interactor
    lateinit var retrofitService: OmdbApi

    override fun onCreate() {
        super.onCreate()

        // init App
        instance = this
        // init repo
        repo = MainRepository()

        // init retrofit
        // a. creating http client
        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            // adding logging interceptor
            .addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            })
            .build()
        // b. creating retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            // adding http client
            .client(okHttpClient)
            .build()
        // c. creating service
        retrofitService = retrofit.create(OmdbApi::class.java)

        // init interactor
        interactor = Interactor(repo, retrofitService)
    }

    companion object {
        // static
        lateinit var instance: App
            // private setter
            private set
    }
}