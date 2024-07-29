package com.ecoapp.domain.usecase

import com.ecoapp.data.model.EcoItem

interface FetchEcoDataUseCase {
    suspend fun execute(): List<EcoItem>
}

