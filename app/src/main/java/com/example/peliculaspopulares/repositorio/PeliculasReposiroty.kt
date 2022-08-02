package com.example.peliculaspopulares.repositorio

import com.example.peliculaspopulares.data.Pagina
import com.example.peliculaspopulares.data.PeliculaID

class PeliculasReposiroty(private val remoteDataSource : PeliculasDataSource) {

    fun getPagina(listener: ResponseListener<Pagina>){
        this.remoteDataSource.getPagina(listener)

    }
    fun getPeliculaID(listener: ResponseListener<PeliculaID>, idpelicula : String){
        this.remoteDataSource.getPeliculaid(listener, idpelicula)

    }



}