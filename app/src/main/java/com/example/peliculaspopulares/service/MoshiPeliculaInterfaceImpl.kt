package com.example.peliculaspopulares.service


import com.example.peliculaspopulares.data.Pagina
import com.example.peliculaspopulares.data.PeliculaID
import retrofit2.Response


class MoshiPeliculaInterfaceImpl : MoshiPeliculaInterface {
    override suspend fun getPeliculas(): Response<Pagina> {

        return PeliculaApiService.service().getPeliculas()

    }
    override suspend fun getPeliculasID(id: Int): PeliculaID {

        return PeliculaApiService.service().getPeliculasID(id)

    }
}