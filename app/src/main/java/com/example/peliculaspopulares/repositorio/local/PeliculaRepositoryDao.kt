package com.example.peliculaspopulares.repositorio.local


import androidx.annotation.WorkerThread
import com.example.peliculaspopulares.data.PeliculasDAO
import com.example.peliculaspopulares.data.PeliculasDAOID
import com.example.peliculaspopulares.service.PeliculaDao
import kotlinx.coroutines.flow.Flow
import kotlin.text.insert


interface MoviesRepository {
    // Gets the movies from the local database
    fun getMoviesStream(): Flow<List<PeliculasDAO>>

    fun getMoviesStreamId(data: String): Flow<PeliculasDAOID>


    // Fetches movies from the network and saves them to the database
    suspend fun refreshMovies(peliculas: List<PeliculasDAO>)

    suspend fun refreshMoviesId(peliculas: List<PeliculasDAOID>)
}

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class PeliculaRepositoryDao(private val peliculaDao : PeliculaDao) : MoviesRepository{

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allPeliculas : Flow<List<PeliculasDAO>> = peliculaDao.getPeliculas()

    val datoDetalle : (String) -> Flow<PeliculasDAOID> = fun (data: String) : Flow<PeliculasDAOID> {
        return peliculaDao.getDetalle(data)
    }
    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(peliculas: List<PeliculasDAO>) {
        peliculaDao.insert(peliculas)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun intertPeli(pelicula: List<PeliculasDAOID>){
        peliculaDao.insertID(pelicula)
    }

    suspend fun delete(){
        peliculaDao.deleteAll()
    }

    suspend fun deleteId() = peliculaDao.deleteAllId()

    override fun getMoviesStream(): Flow<List<PeliculasDAO>> {
        return peliculaDao.getPeliculas()
    }

    override fun getMoviesStreamId(data: String): Flow<PeliculasDAOID> {
        return peliculaDao.getDetalle(data)
    }

    override suspend fun refreshMovies(peliculas: List<PeliculasDAO>) {
        try {
            peliculaDao.insert(peliculas)
        } catch (e: Exception) {

        }
    }

    override suspend fun refreshMoviesId(peliculas: List<PeliculasDAOID>) {
        try {
            peliculaDao.insertID(peliculas)
        } catch (e: Exception) {
        }

    }


}

