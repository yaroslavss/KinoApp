package com.yara.kinoapp.di

import com.yara.kinoapp.BuildConfig
import com.yara.kinoapp.data.ApiConstants
import com.yara.kinoapp.data.MainRepository
import com.yara.kinoapp.data.OmdbApi
import com.yara.kinoapp.domain.Interactor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DI {
    val mainModule = module {
        // creating repository
        single { MainRepository() }
        // creating object for OMDB interaction
        single<OmdbApi> {
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
            retrofit.create(OmdbApi::class.java)
        }
        // creating interactor
        single { Interactor(get(), get()) }
    }
}