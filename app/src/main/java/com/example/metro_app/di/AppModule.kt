package com.example.metro_app.di

import android.content.Context
import com.example.metro_app.data.datasource.MetroJsonDataSource
import com.example.metro_app.data.repository.MetroRepositoryImpl
import com.example.metro_app.domain.usecase.BFSUseCase
import com.example.metro_app.domain.usecase.CalculateFareUseCase
import com.example.metro_app.domain.usecase.CalculateTimeUseCase
import com.example.metro_app.domain.usecase.FindRouteUseCase
import com.example.metro_app.domain.usecase.GetDirectionUseCase
import com.example.metro_app.domain.usecase.GetFirstStationUseCase
import com.example.metro_app.domain.usecase.GetLastStationUseCase

import com.example.metro_app.presentation.feature.home.HomeViewModel

object AppModule {

    fun provideViewModel(context: Context): HomeViewModel {

        val dataSource = MetroJsonDataSource(context)
        val repository = MetroRepositoryImpl(dataSource)

        val fare = CalculateFareUseCase()
        val time = CalculateTimeUseCase(repository)
        val bfsUseCase = BFSUseCase()

        val findRoute = FindRouteUseCase(
            repository,
            fare,
            time,
            bfsUseCase
        )

        val getFirstStationUseCase = GetFirstStationUseCase(repository)
        val getLastStationUseCase = GetLastStationUseCase(repository)

        val direction = GetDirectionUseCase(
            getFirstStationUseCase,
            getLastStationUseCase
        )

        return HomeViewModel(
            findRoute, direction,
            repository = repository
        )
    }
}