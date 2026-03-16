package com.example.metro_app.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object SplashRoute

@Serializable
object HomeRoute

@Serializable
data class DetailsRoute(
    val startDestination: String,
    val endDestination: String
)

@Serializable
object MapRoute

@Serializable
object FavoritesRoute
