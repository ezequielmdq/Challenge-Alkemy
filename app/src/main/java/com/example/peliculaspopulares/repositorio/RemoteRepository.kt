package com.example.peliculaspopulares.repositorio

import com.example.peliculaspopulares.data.Pagina
import com.example.peliculaspopulares.data.PeliculaID
import javax.inject.Inject


class RemoteRepository@Inject constructor(private val remotedatasource : RemoteDataSource) {
    suspend fun fetchPeliculas() : Pagina? {
        return this.remotedatasource.fetchPagina()

    }
     suspend fun fetchPeliculaID(idpelicula : String): PeliculaID? {
        return this.remotedatasource.fetchPeliculaId(idpelicula)

    }

}