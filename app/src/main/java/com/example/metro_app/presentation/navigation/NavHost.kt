package com.example.metro_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.metro_app.di.AppModule
import com.example.metro_app.presentation.feature.details.StationScreen
import com.example.metro_app.presentation.feature.home.HomeScreen
import com.example.metro_app.presentation.feature.home.HomeViewModel
import com.example.metro_app.presentation.feature.splash.SplashScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: HomeViewModel = AppModule.provideViewModel(context)

    NavHost(
        navController = navController,
        startDestination = SplashRoute
    ) {
        composable<SplashRoute> {
            SplashScreen(
                onTimeout = {
                    navController.navigate(HomeRoute) {
                        popUpTo(SplashRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<HomeRoute> {
            val uiState by viewModel.uiState.collectAsState()

            HomeScreen(
                uiState = uiState,
                onFindRouteClick = { from, to ->
                    viewModel.findRoute(from, to)
                },
                onNavigateToDetails = { from, to ->
                    navController.navigate(DetailsRoute(from, to))
                },
                onRemoveRecentRoute = { route ->
                    viewModel.removeRecentRoute(route)
                }
            )
        }

        composable<DetailsRoute> { backStackEntry ->
            val route: DetailsRoute = backStackEntry.toRoute()

            StationScreen(
                startStation = route.startDestination,
                endStation = route.endDestination,
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}