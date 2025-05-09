package com.example.peliculaspopulares

import android.app.Application
import com.example.peliculaspopulares.repositorio.AppContainer
import com.example.peliculaspopulares.repositorio.DefaultAppContainer


class PeliculasApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }

}