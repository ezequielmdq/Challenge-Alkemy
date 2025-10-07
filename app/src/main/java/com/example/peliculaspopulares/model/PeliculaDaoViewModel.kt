package com.example.peliculaspopulares.model

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.peliculaspopulares.PeliculasApplication
import com.example.peliculaspopulares.data.PeliculasDAO
import com.example.peliculaspopulares.data.PeliculasDAOID
import com.example.peliculaspopulares.repositorio.local.PeliculaRepositoryDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException


sealed interface MoviesUiStateDao {
    data class Success(val photos: List<PeliculasDAO>) : MoviesUiStateDao
    object Error : MoviesUiStateDao
    object Loading : MoviesUiStateDao
}


data class MoviesDao(val photos: List<PeliculasDAO> = emptyList())



sealed interface MoviesUiStateIdDao {
    data class Success(val id: String,
                       val titulo: String,
                       val descipcion: String,
                       val fechalanzamiento: String,
                       val porcenjatevotos: Float,
                       val lenguaje: String,
        //val genero: List<Generodetalles>,
                       val poster: String) : MoviesUiStateIdDao
    object Error : MoviesUiStateIdDao
    object Loading : MoviesUiStateIdDao
}

data class MoviesUiStateIdDaoDetalle(
    var id: String = "",
    var titulo: String = "",
    var descipcion: String = "",
    var fechalanzamiento: String = "",
    var porcenjatevotos: Float = 0f,
    var lenguaje: String = "",
    //val genero: List<Generodetalles> = emptyList(),
    var poster: String = "")

class PeliculaDaoViewModel (private val repository: PeliculaRepositoryDao) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
/**
    private val _allPelis = repository.allPeliculas.asLiveData()
    private val _descripcion = MutableLiveData<String>()
    private val _fechalanzamiento = MutableLiveData<String>()
    private val _porcentajevotos = MutableLiveData<Float>()
    private val _lenguaje = MutableLiveData<String>()
    private val _backdrop = MutableLiveData<String>()

    val allPelis : LiveData<List<PeliculasDAO>> = _allPelis
    val descripcion : LiveData<String> = _descripcion
    val fchalanzamiento : LiveData<String> = _fechalanzamiento
    val porcentajevotos : LiveData<Float> = _porcentajevotos
    val lenguaje : LiveData<String> = _lenguaje
    val backdrop :LiveData<String> = _backdrop

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(peliculas: List<PeliculasDAO>) = viewModelScope.launch {
        try {
            repository.insert(peliculas)
        }catch (e:Exception){}
    }

    fun insertID(pelicula: List<PeliculasDAOID>) = viewModelScope.launch {
        try {
            repository.intertPeli(pelicula)
        }catch (e:Exception){}
    }

    fun datoDetalle(data : String)  = viewModelScope.launch {
        try {
        repository.datoDetalle(data).collect { data ->

            _descripcion.value = data.descipcion
            _fechalanzamiento.value = data.fechalanzamiento
            _lenguaje.value = data.lenguaje
            _porcentajevotos.value = data.porcenjatevotos
            _backdrop.value = data.poster

        }}catch (e:Exception){}
    }

    fun delete() = viewModelScope.launch {
        try {
            repository.delete()
        } catch (e:Exception){}
    }


    class PeliculaDaoViewModelFactory(private val repository: PeliculaRepositoryDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PeliculaDaoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PeliculaDaoViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }*/


var moviesUiState: MoviesUiStateDao by mutableStateOf(MoviesUiStateDao.Loading)
    private set
    var moviesUiStateId: MoviesUiStateIdDao by mutableStateOf(
        MoviesUiStateIdDao.Loading)
        private set


    init {

        getMovies()

        //x()
    }

    private val _moviesUiStateIdDaoDetalle = MutableStateFlow(MoviesUiStateIdDaoDetalle())
    private val _moviesUiStateDao = MutableStateFlow(MoviesDao())

    val moviesUiStateIdDaoDetallee: StateFlow<MoviesUiStateIdDaoDetalle> = _moviesUiStateIdDaoDetalle.asStateFlow()
    val moviesUiStateDao: StateFlow<MoviesDao> = _moviesUiStateDao.asStateFlow()

    fun delete() = viewModelScope.launch {
        try {
            repository.delete()

        }catch (e:Exception){}
    }

    fun deleteId() = viewModelScope.launch {
        try {
            repository.deleteId()
        } catch (e:Exception){
        }
    }


    /**
     * Holds current item ui state
     */
    /**fun x() {

    moviesUiState = MoviesUiStateDao.Success(moviesUiStateDao.value.photos)
    }*/
    @SuppressLint("SuspiciousIndentation")
    fun getMovies() {
        viewModelScope.launch {
            try{
                repository.getMoviesStream().collect { data ->
                    moviesUiState = MoviesUiStateDao.Success(photos = data)
                    //repository.getMoviesStream().collect { data ->
                    //_moviesUiStateDao.value = MoviesDao(photos = data.toList())
                }
            }catch (e:Exception){}
        }
    }
    fun refreshMoviesData(peliculas: List<PeliculasDAO>) {
        viewModelScope.launch {
            try {
                repository.refreshMovies(peliculas)
            }  catch (e: IOException) {
            }
        }
    }

    fun refreshMoviesDataId(pelicula: List<PeliculasDAOID>) {
        viewModelScope.launch {
            try {
                repository.refreshMoviesId(pelicula)
            }catch (e:Exception){}
        }
    }

    fun getMoviesId(data : String)  = viewModelScope.launch {
        try {
            repository.getMoviesStreamId(data).collect { data ->

                moviesUiStateId = MoviesUiStateIdDao.Success(
                    descipcion = data.descipcion,
                    fechalanzamiento = data.fechalanzamiento,
                    porcenjatevotos = data.porcenjatevotos,
                    lenguaje = data.lenguaje,
                    poster = data.poster,
                    id = data.id,
                    titulo = data.titulo
                )


                /** _moviesUiStateIdDaoDetalle.value.descipcion = data.descipcion
                _moviesUiStateIdDaoDetalle.value.fechalanzamiento = data.fechalanzamiento
                _moviesUiStateIdDaoDetalle.value.lenguaje = data.lenguaje
                _moviesUiStateIdDaoDetalle.value.porcenjatevotos = data.porcenjatevotos
                _moviesUiStateIdDaoDetalle.value.poster = data.poster
                _moviesUiStateIdDaoDetalle.value.id = data.id
                _moviesUiStateIdDaoDetalle.value.titulo = data.titulo*/

            }}catch (e:Exception){}
    }









    /** class PeliculaDaoViewModelFactory(private val repository: PeliculaRepositoryDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(PeliculaDaoViewModel::class.java)) {
    @Suppress("UNCHECKED_CAST")
    return PeliculaDaoViewModel(repository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
    }

    }*/

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PeliculasApplication)
                val repository = application.container.peliculaRepositoryDao
                PeliculaDaoViewModel(
                    repository = repository,
                )
            }
        }
    }
}




