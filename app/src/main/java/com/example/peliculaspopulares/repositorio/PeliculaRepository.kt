package com.example.peliculaspopulares.repositorio


import com.example.peliculaspopulares.data.Pagina
import com.example.peliculaspopulares.data.PeliculaID
import com.example.peliculaspopulares.service.MoshiPeliculaInterface
import retrofit2.Response


interface PeliculaRepository {

    suspend fun getPeliculas(): Response<Pagina>

    suspend fun getPeliculasID (id: String): Response<PeliculaID>

}

class NetworkPeliculaRepository(private val moshiPeliculaInterface: MoshiPeliculaInterface) : PeliculaRepository {

    override suspend fun getPeliculas(): Response<Pagina> = moshiPeliculaInterface.getPeliculas()

    override suspend fun getPeliculasID(id: String): Response<PeliculaID> = moshiPeliculaInterface.getPeliculasID(id)

}