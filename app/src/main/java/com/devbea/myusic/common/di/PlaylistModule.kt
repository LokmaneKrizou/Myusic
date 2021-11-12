package com.devbea.myusic.common.di

import com.devbea.myusic.common.api.PlaylistAPI
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val client = OkHttpClient()
val idlingResource = OkHttp3IdlingResource.create("okhttp", client)

@Module
@InstallIn(FragmentComponent::class)
class PlaylistModule {

    @Provides
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:2999/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    fun playlistAPI(retrofit: Retrofit): PlaylistAPI = retrofit.create(PlaylistAPI::class.java)
}