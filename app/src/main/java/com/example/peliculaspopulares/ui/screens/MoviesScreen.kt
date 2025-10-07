package com.example.peliculaspopulares.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.peliculaspopulares.BuildConfig
import com.example.peliculaspopulares.R
import com.example.peliculaspopulares.data.PeliculasDAO
import com.example.peliculaspopulares.data.PeliculasDAOID
import com.example.peliculaspopulares.model.MoviesDao
import com.example.peliculaspopulares.model.MoviesList
import com.example.peliculaspopulares.model.MoviesListId
import com.example.peliculaspopulares.model.MoviesUiStateDao
import com.example.peliculaspopulares.model.PeliculaDaoViewModel
import com.example.peliculaspopulares.model.PeliculasPopularesViewModel
import com.example.peliculaspopulares.ui.theme.PeliculasPopularesTheme
import kotlin.collections.map


@Composable
fun MoviesScreen(
    moviesList: MoviesList,
    moviesListId: MoviesListId,
    moviesUiState: MoviesUiStateDao,
    moviesUiStateDao: MoviesDao,
    onMovieClick: (String) -> Unit,
    retryAction: () -> Unit,
    modifier: Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    peliculaViewModel: PeliculasPopularesViewModel = viewModel(factory = PeliculasPopularesViewModel.Factory),
    peliculaViewModelDao: PeliculaDaoViewModel = viewModel(factory = PeliculaDaoViewModel.Factory)
) {

    // Otros LaunchedEffect para las otras operaciones
    LaunchedEffect(moviesList.movies) {
        if (moviesList.movies.isNotEmpty()) {
            peliculaViewModelDao.delete()
            peliculaViewModelDao.refreshMoviesData(moviesList.movies.map {
                PeliculasDAO(it.id, it.titulo, it.posterpath, it.backdrop)
            })
        }
    }



    // SOLUCIÓN PARA EL CÓDIGO SELECCIONADO
// Este bloque se ejecutará cada vez que la lista moviesUiStateIdLista.movies cambie.
    LaunchedEffect(moviesListId.movies) {
        // Solo ejecutamos si la lista no está vacía para evitar trabajo innecesario
        if (moviesListId.movies.isNotEmpty()) {
            peliculaViewModelDao.deleteId()
            peliculaViewModelDao.refreshMoviesDataId(moviesListId.movies.map {
                PeliculasDAOID(
                    it.id,
                    it.titulo,
                    it.descipcion,
                    it.porcenjatevotos,
                    it.lenguaje,
                    it.fechalanzamiento,
                    it.poster
                )
            })
        }
    }

    when (moviesUiState) {
        is MoviesUiStateDao.Loading -> LoadingScreen()
        is MoviesUiStateDao.Success -> PhotosGridScreen(photos = moviesUiState.photos ,  onMovieClick = {onMovieClick(it)}, modifier = modifier)
        else -> ErrorScreen(retryAction)
    }
}

/**
 * ResultScreen displaying number of photos retrieved.
 */
@Composable
fun ResultScreen(photos: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = photos)
    }
}

@Composable
fun LoadingScreen() {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun MoviesPhotoCard(photo: PeliculasDAO, onMovieClick: (String) -> Unit) {


    Card(
        modifier = Modifier
            .height(500.dp)
            .fillMaxSize(),
        onClick = { onMovieClick(photo.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = CardDefaults.shape
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(BuildConfig.BASE_URL_IMAGEN + photo.posterpath)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.movies_photo),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize(),

                )
        }
    }
}


@Composable
fun PhotosGridScreen(
    modifier: Modifier,
    photos: List<PeliculasDAO>,
    onMovieClick: (String) -> Unit,
    contentPadding: PaddingValues = PaddingValues(16.dp),
) {

    val state = rememberPagerState { photos.size }

    HorizontalPager(state = state,
        modifier = modifier
            .wrapContentHeight()
            .fillMaxSize(),
        contentPadding = contentPadding,
        pageSpacing = 20.dp
        ) { page ->

        MoviesPhotoCard(photos[page], onMovieClick = onMovieClick)

    }
}

@Preview(showBackground = true)
@Composable
fun PhotosGridScreenPreview() {
    PeliculasPopularesTheme {
        val mockData = List(10) { PeliculasDAO("$it", "", "", "") }
        PhotosGridScreen(
            photos = mockData,
            onMovieClick = { },
            modifier = Modifier
        )
    }
}



@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    PeliculasPopularesTheme {
        ResultScreen(stringResource(R.string.placeholder_result))
    }
}