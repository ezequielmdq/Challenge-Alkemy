package com.example.peliculaspopulares.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.peliculaspopulares.repositorio.PeliculasDataSource
import com.example.peliculaspopulares.repositorio.PeliculasReposiroty
import com.example.peliculaspopulares.repositorio.RemoteDataSource
import com.example.peliculaspopulares.repositorio.RemoteRepository

class PeliculasPopularesViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val dataSource = PeliculasDataSource()
        val repository = PeliculasReposiroty(dataSource)
        val dataSource2 = RemoteDataSource()
        val repository2 = RemoteRepository(dataSource2)
        return PeliculasPopularesViewModel(repository, repository2) as T
    }


}