package com.example.peliculaspopulares.repositorio

import com.example.peliculaspopulares.data.Pagina
import com.example.peliculaspopulares.data.PeliculaID
import com.example.peliculaspopulares.service.PeliculaApiService
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(){
    suspend fun fetchPagina() : Pagina? =
        PeliculaApiService.service().getPeliculas().body()


    suspend fun fetchPeliculaId(idpelicula: String) : PeliculaID =
        PeliculaApiService.service().getPeliculasID(idpelicula.toInt())


}