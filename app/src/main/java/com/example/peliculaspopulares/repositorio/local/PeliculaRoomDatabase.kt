package com.example.peliculaspopulares.repositorio.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.peliculaspopulares.data.GeneroDAO
import com.example.peliculaspopulares.data.PeliculasDAO
import com.example.peliculaspopulares.data.PeliculasDAOID
import com.example.peliculaspopulares.service.PeliculaDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


// Annotates class to be a Room Database with a table (entity) of the PeliculaDAO class
@Database(entities = [PeliculasDAO::class, PeliculasDAOID::class, GeneroDAO::class], version = 3, exportSchema = false)
public abstract class PeliculaRoomDatabase : RoomDatabase(){

    abstract fun peliculaDao(): PeliculaDao

    private class PeliculaDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {

                    val peliculaDao = database.peliculaDao()

                    // Delete all content here.
                    peliculaDao.deleteAll()

                    peliculaDao.deleteAllId()

                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PeliculaRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PeliculaRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PeliculaRoomDatabase::class.java,
                    "pelicula_database"
                )
                    .addCallback(PeliculaDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}