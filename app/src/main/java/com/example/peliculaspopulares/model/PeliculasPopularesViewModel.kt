package com.example.peliculaspopulares.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.peliculaspopulares.data.Pagina
import com.example.peliculaspopulares.data.PeliculaID
import com.example.peliculaspopulares.data.Peliculas
import com.example.peliculaspopulares.data.Generodetalles
import com.example.peliculaspopulares.repositorio.PeliculasReposiroty
import com.example.peliculaspopulares.repositorio.RepositoryError
import com.example.peliculaspopulares.repositorio.RepositoryResponse
import com.example.peliculaspopulares.repositorio.ResponseListener

class PeliculasPopularesViewModel(private val repository : PeliculasReposiroty) : ViewModel() {


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

    fun cargar() {

        repository.getPagina(listener = object : ResponseListener<Pagina> {
            override fun onResponse(response: RepositoryResponse<Pagina>) {

                _pelis.value = response.data.results
            }

            override fun onError(error: RepositoryError) {
                println("no funciona")
                println(error.message)
                println(error.code)
                _errorconexion.value = error.message


            }
        }
        )}

        fun cargarID(idpelicula : String) {

            repository.getPeliculaID(listener = object : ResponseListener<PeliculaID> {
                override fun onResponse(response: RepositoryResponse<PeliculaID>) {
                    _pelisid.value = response.data.descipcion
                    _pelisfechalanzamiento.value = response.data.fechalanzamiento
                    _peliscalificacion.value = response.data.porcenjatevotos
                    _pelislenguaje.value = response.data.lenguaje
                    _pelisgenero.value = response.data.genero
                    _poster.value = response.data.poster

                }

                override fun onError(error: RepositoryError) {

                }

            },idpelicula)


        }




}













