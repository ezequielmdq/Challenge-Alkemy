package com.example.peliculaspopulares.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.peliculaspopulares.data.PeliculasDAO
import com.example.peliculaspopulares.data.PeliculasDAOID
import com.example.peliculaspopulares.repositorio.local.PeliculaRepositoryDao
import kotlinx.coroutines.launch

class PeliculaDaoViewModel (private val repository: PeliculaRepositoryDao) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    private val _allPelis = repository.allPeliculas.asLiveData()
    private val _descripcion = MutableLiveData<String>()
    private val _fechalanzamiento = MutableLiveData<String>()
    private val _porcentajevotos = MutableLiveData<Float>()
    private val _lenguaje = MutableLiveData<String>()
    private val _backdrop = MutableLiveData<String>()

    val allPelis : LiveData<List<PeliculasDAO>> = _allPelis
    val descripcion : LiveData<String> = _descripcion
    val fchalanzamiento : LiveData<String> = _fechalanzamiento
    val porcentajevotos : LiveData<Float> = _porcentajevotos
    val lenguaje : LiveData<String> = _lenguaje
    val backdrop :LiveData<String> = _backdrop

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(peliculas: List<PeliculasDAO>) = viewModelScope.launch {
        try {
            repository.insert(peliculas)
        }catch (e:Exception){}
    }

    fun insertID(pelicula: List<PeliculasDAOID>) = viewModelScope.launch {
        try {
            repository.intertPeli(pelicula)
        }catch (e:Exception){}
    }

    fun datoDetalle(data : String)  = viewModelScope.launch {
        try {
        repository.datoDetalle(data).collect { data ->

            _descripcion.value = data.descipcion
            _fechalanzamiento.value = data.fechalanzamiento
            _lenguaje.value = data.lenguaje
            _porcentajevotos.value = data.porcenjatevotos
            _backdrop.value = data.poster

        }}catch (e:Exception){}
    }

    fun delete() = viewModelScope.launch {
        try {
            repository.delete()
        } catch (e:Exception){}
    }


    class PeliculaDaoViewModelFactory(private val repository: PeliculaRepositoryDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PeliculaDaoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PeliculaDaoViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}



