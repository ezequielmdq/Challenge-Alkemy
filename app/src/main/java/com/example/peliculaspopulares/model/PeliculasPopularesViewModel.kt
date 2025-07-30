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
import com.example.peliculaspopulares.data.PeliculaID
import com.example.peliculaspopulares.repositorio.network.PeliculaRepository
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

    private val _listapeliculasid = MutableLiveData<List<PeliculaID>>()

    private val _pelisfechalanzamientodao = MutableLiveData<String>()
    private val _pelisiddao = MutableLiveData<String>()
    private val _pelistitulodao = MutableLiveData<String>()
    private val _pelisdescripciondao = MutableLiveData<String>()
    private val _peliscalificaciondao = MutableLiveData<Float>()
    private val _pelisgenerodao = MutableLiveData<List<Generodetalles>>()
    private val _pelislenguajedao = MutableLiveData<String>()
    private val _errorconexiondao = MutableLiveData<String>()
    private val _posterdao = MutableLiveData<String>()

    val pelis: LiveData<List<Peliculas>> = _pelis
    val pelisfechalanzamiento : LiveData<String> = _pelisfechalanzamiento
    val pelisid: LiveData<String> = _pelisid
    val peliscalificacion : LiveData<Float> = _peliscalificacion
    val pelisgenero : LiveData<List<Generodetalles>> = _pelisgenero
    val pelislenguaje : LiveData<String> = _pelislenguaje
    val errorconexion : LiveData<String> = _errorconexion
    val poster : LiveData<String> = _poster
    val pelisfechalanzamientodao : LiveData<String> = _pelisfechalanzamientodao
    val pelisiddao: LiveData<String> = _pelisiddao
    val pelistitulodao: LiveData<String> = _pelistitulodao
    val pelisdescipciondao: LiveData<String> = _pelisdescripciondao
    val peliscalificaciondao : LiveData<Float> = _peliscalificaciondao
    val pelisgenerodao : LiveData<List<Generodetalles>> = _pelisgenerodao
    val pelislenguajedao : LiveData<String> = _pelislenguajedao
    val errorconexiondao : LiveData<String> = _errorconexiondao
    val posterdao : LiveData<String> = _posterdao


    val listapeliculasid : LiveData<List<PeliculaID>> = _listapeliculasid

    fun getPeliculas() {

        viewModelScope.launch {
            try {

                _pelis.value = repository.getPeliculas().body()?.results

            } catch (e: Exception) {

            }
        }
    }


    fun getPeliculaId(idpelicula: String) {

        viewModelScope.launch {

            try {

                _pelisid.value = repository.getPeliculasID(idpelicula).body()?.descipcion
                _pelisfechalanzamiento.value = repository.getPeliculasID(idpelicula).body()?.fechalanzamiento
                _peliscalificacion.value = repository.getPeliculasID(idpelicula).body()?.porcenjatevotos
                _pelislenguaje.value = repository.getPeliculasID(idpelicula).body()?.lenguaje
                _pelisgenero.value = repository.getPeliculasID(idpelicula).body()?.genero
                _poster.value = repository.getPeliculasID(idpelicula).body()?.poster

            } catch (e: Exception) {

            }
        }
    }



    /**      @SuppressLint("SuspiciousIndentation")
    fun getPeliculaIdDao(idpelicula: String) {

    viewModelScope.launch {

    try {
    _pelisiddao.value = repository3.getPeliculasIDDao(idpelicula).body()?.id
    _pelistitulodao.value = repository3.getPeliculasIDDao(idpelicula).body()?.titulo
    _pelisdescripciondao.value = repository3.getPeliculasIDDao(idpelicula).body()?.descipcion
    _peliscalificaciondao.value = repository3.getPeliculasIDDao(idpelicula).body()?.porcenjatevotos
    _pelislenguajedao.value = repository3.getPeliculasIDDao(idpelicula).body()?.lenguaje
    _pelisfechalanzamientodao.value = repository3.getPeliculasIDDao(idpelicula).body()?.fechalanzamiento
    _posterdao.value = repository3.getPeliculasIDDao(idpelicula).body()?.poster

    } catch (e: Exception) {
    }
    }
    }*/

    fun getListaPeliId(){

        viewModelScope.launch {

            try {
                repository.getListaPelisId().collect{ data->

                    _listapeliculasid.value = data

                }

            }catch (e: Exception){}

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













