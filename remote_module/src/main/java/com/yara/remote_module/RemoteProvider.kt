package com.yara.remote_module

interface RemoteProvider {
    fun provideRemote(): OmdbApi
}