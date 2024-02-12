package com.example.peliculaspopulares.service

import com.example.peliculaspopulares.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object PeliculaApiService {
    @Provides
    @Singleton
    fun service() : MoshiPeliculaInterface {

        val moshi = Moshi.Builder()
             .add(KotlinJsonAdapterFactory())
             .build()

        val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BuildConfig.BASE_URL)
        .build()

        val retrofitService : MoshiPeliculaInterface by lazy {
        retrofit.create(MoshiPeliculaInterface::class.java) }

        return retrofitService
    }
}