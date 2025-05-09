package com.example.peliculaspopulares.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.peliculaspopulares.PeliculasApplication
import com.example.peliculaspopulares.data.Peliculas
import com.example.peliculaspopulares.data.Generodetalles
import com.example.peliculaspopulares.repositorio.PeliculaRepository
import kotlinx.coroutines.launch


class PeliculasPopularesViewModel(private val repository: PeliculaRepository) : ViewModel() {


    //variables viewmodel

    private val _pelis = MutableLiveData<List<Peliculas>>()
    private val _pelisfechalanzamiento = MutableLiveData<String>()
    private val _pelisid = MutableLiveData<String>()
    private val _peliscalificacion = MutableLiveData<Float>()
    private val _pelisgenero = MutableLiveData<List<Generodetalles>>()
    private val _pelislenguaje = MutableLiveData<String>()
    private val _errorconexion = MutableLiveData<String>()
    private val _poster = MutableLiveData<String>()

    val pelis: LiveData<List<Peliculas>> = _pelis
    val pelisfechalanzamiento : LiveData<String> = _pelisfechalanzamiento
    val pelisid: LiveData<String> = _pelisid
    val peliscalificacion : LiveData<Float> = _peliscalificacion
    val pelisgenero : LiveData<List<Generodetalles>> = _pelisgenero
    val pelislenguaje : LiveData<String> = _pelislenguaje
    val errorconexion : LiveData<String> = _errorconexion
    val poster : LiveData<String> = _poster


    // Metodos para consultar api



    fun getPeliculas() {
        viewModelScope.launch {
            try {
                _pelis.value = repository.getPeliculas().body()?.results

            } catch (e: Exception) {

            }
        }
    }

    fun getPeliculaId(idpelicula : String) {
        viewModelScope.launch {
            try {
                _pelisid.value = repository.getPeliculasID(idpelicula).body()?.descipcion
                _pelisfechalanzamiento.value = repository.getPeliculasID(idpelicula).body()?.fechalanzamiento
                _peliscalificacion.value = repository.getPeliculasID(idpelicula).body()?.porcenjatevotos
                _pelislenguaje.value = repository.getPeliculasID(idpelicula).body()?.lenguaje
                _pelisgenero.value = repository.getPeliculasID(idpelicula).body()?.genero
                _poster.value = repository.getPeliculasID(idpelicula).body()?.poster
            }catch (e: Exception){

            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PeliculasApplication)
                val repository = application.container.peliculaRepository
                PeliculasPopularesViewModel(
                    repository = repository,
                )
            }
        }
    }
}













