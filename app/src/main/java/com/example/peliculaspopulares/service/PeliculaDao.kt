package com.example.peliculaspopulares.service


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.peliculaspopulares.data.PeliculasDAO
import com.example.peliculaspopulares.data.PeliculasDAOID
import kotlinx.coroutines.flow.Flow


@Dao
interface PeliculaDao {

    @Query("SELECT * FROM pelicula_table")
    fun getPeliculas() : Flow<List<PeliculasDAO>>

    @Query("SELECT ID, original_title, vote_average, original_language, release_date, backdrop_path, overview FROM peliculaID_table WHERE ID = :id")
    fun getDetalle(id: String) : Flow<PeliculasDAOID>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pelicula: List<PeliculasDAO>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertID(pelicula: List<PeliculasDAOID>)

    @Query("DELETE FROM pelicula_table")
    suspend fun deleteAll()

    @Query("DELETE FROM peliculaID_table")
    suspend fun deleteAllId()

}