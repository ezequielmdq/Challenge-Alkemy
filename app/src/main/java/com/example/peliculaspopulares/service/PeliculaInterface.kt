package com.example.peliculaspopulares.service



import com.example.peliculaspopulares.BuildConfig
import com.example.peliculaspopulares.data.Pagina
import com.example.peliculaspopulares.data.PeliculaID
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface PeliculaInterface{


    @GET( BuildConfig.PATH_PELICULAS + BuildConfig.QUERY_POPULAR + BuildConfig.API_KEY + BuildConfig.QUERY_LENGUAJE)
    fun getPeliculas () : Call<Pagina>


    @GET(BuildConfig.PATH_PELICULAS + BuildConfig.QUERY_PELICULAID + BuildConfig.API_KEY + BuildConfig.QUERY_LENGUAJE)
    fun getPeliculasID (@Path("movieid") id: String) : Call<PeliculaID>



}