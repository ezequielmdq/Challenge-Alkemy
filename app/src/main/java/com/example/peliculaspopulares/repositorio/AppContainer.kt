package com.example.peliculaspopulares.repositorio

import com.example.peliculaspopulares.BuildConfig
import com.example.peliculaspopulares.service.MoshiPeliculaInterface
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {

    val peliculaRepository : PeliculaRepository

}

class DefaultAppContainer : AppContainer {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    private val retrofitService : MoshiPeliculaInterface by lazy {
        retrofit.create(MoshiPeliculaInterface::class.java) }

    override val peliculaRepository: PeliculaRepository by lazy {
        NetworkPeliculaRepository(retrofitService) }


}