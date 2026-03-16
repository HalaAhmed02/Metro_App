package com.example.metro_app.domain.usecase

import com.example.metro_app.domain.repository.MetroRepository

class CalculateTimeUseCase(private val repo: MetroRepository) {
    operator fun invoke(count: Int) = count * repo.getTravelTime()
}