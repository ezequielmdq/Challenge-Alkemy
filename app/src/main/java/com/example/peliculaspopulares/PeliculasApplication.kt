package com.example.peliculaspopulares

import android.app.Application
import com.example.peliculaspopulares.repositorio.local.PeliculaRepositoryDao
import com.example.peliculaspopulares.repositorio.local.PeliculaRoomDatabase
import com.example.peliculaspopulares.repositorio.network.AppContainer
import com.example.peliculaspopulares.repositorio.network.DefaultAppContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class PeliculasApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { PeliculaRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { PeliculaRepositoryDao(database.peliculaDao()) }


    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }

}