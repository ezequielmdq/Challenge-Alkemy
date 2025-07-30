package com.example.peliculaspopulares.repositorio.network


import com.example.peliculaspopulares.data.Pagina
import com.example.peliculaspopulares.data.PeliculaID
import com.example.peliculaspopulares.data.Peliculas
import com.example.peliculaspopulares.service.MoshiPeliculaInterface
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response


interface PeliculaRepository {

    suspend fun getPeliculas(): Response<Pagina>

    suspend fun getPeliculasID (id: String): Response<PeliculaID>

    suspend fun getListaPelisId() : Flow<List<PeliculaID>>

}

class NetworkPeliculaRepository(private val moshiPeliculaInterface: MoshiPeliculaInterface, private val refreshIntervalMs: Long = 50) :
    PeliculaRepository {

    override suspend fun getPeliculas(): Response<Pagina> = moshiPeliculaInterface.getPeliculas()

    override suspend fun getPeliculasID(id: String): Response<PeliculaID> = moshiPeliculaInterface.getPeliculasID(id)

    override suspend fun getListaPelisId(): Flow<List<PeliculaID>> = flow{

        val listaPelis : List<Peliculas>? = moshiPeliculaInterface.getPeliculas().body()?.results
        val listaPelisId = mutableListOf<PeliculaID>()

        if (listaPelis != null) {
            for(i in listaPelis.listIterator()){
                val peliId = moshiPeliculaInterface.getPeliculasID(i.id).body()
                if (peliId != null) {
                    listaPelisId.add(peliId)
                }
                emit(listaPelisId)
                delay(refreshIntervalMs)
            }
        }
    }

}