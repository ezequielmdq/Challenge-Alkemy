package com.example.peliculaspopulares.ui

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.peliculaspopulares.R
import com.example.peliculaspopulares.model.PeliculaDaoViewModel
import com.example.peliculaspopulares.model.PeliculasPopularesViewModel
import com.example.peliculaspopulares.ui.screens.DetailsScreen
import com.example.peliculaspopulares.ui.screens.MoviesScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext
import kotlin.getValue

enum class MoviesScreenApp(@StringRes val title: Int) {
    Start(title = R.string.movies),
    Details(title = R.string.details),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesAppBar(
    currentScreen : MoviesScreenApp,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    CenterAlignedTopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}



@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesApp(modifier: Modifier, navController: NavHostController = rememberNavController()) {

    val peliculaViewModel: PeliculasPopularesViewModel = viewModel(factory = PeliculasPopularesViewModel.Factory)

    val peliculaViewModelDao: PeliculaDaoViewModel = viewModel(factory = PeliculaDaoViewModel.Factory)

    val moviesList by peliculaViewModel.moviiesList.collectAsState()
    val moviesListId by peliculaViewModel.moviesListId.collectAsState()

    val moviesUiStateDao by peliculaViewModelDao.moviesUiStateDao.collectAsState()


    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = MoviesScreenApp.valueOf(
        backStackEntry?.destination?.route ?: MoviesScreenApp.Start.name
    )

    Scaffold(

            topBar = {
                MoviesAppBar(
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() },
                )
            },

        ) { innerPadding ->

        Surface(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {




            NavHost(
                navController = navController,
                startDestination = MoviesScreenApp.Start.name,
                modifier = Modifier.fillMaxSize()

            ) {

                composable(route = MoviesScreenApp.Start.name) {
                        MoviesScreen(
                            moviesList = moviesList,
                            moviesListId = moviesListId,
                            moviesUiState = peliculaViewModelDao.moviesUiState,
                            moviesUiStateDao = moviesUiStateDao,
                            retryAction = {  },
                            onMovieClick = { //peliculaViewModel.getMoviesId(it)
                                peliculaViewModelDao.getMoviesId(it)
                                navController.navigate(MoviesScreenApp.Details.name) },
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                composable(route = MoviesScreenApp.Details.name) {
                    DetailsScreen(
                        moviesUiStateId = peliculaViewModelDao.moviesUiStateId,
                        retryAction = {  },
                        modifier = Modifier.fillMaxSize()
                    )
                }


            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}