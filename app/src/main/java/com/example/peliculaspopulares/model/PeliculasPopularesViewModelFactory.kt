package com.example.peliculaspopulares.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.peliculaspopulares.repositorio.PeliculasDataSource
import com.example.peliculaspopulares.repositorio.PeliculasReposiroty

class PeliculasPopularesViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val dataSource = PeliculasDataSource()
        val reposiroty = PeliculasReposiroty(dataSource)
        return PeliculasPopularesViewModel(reposiroty) as T
    }


}