package com.ecoapp.domain.usecase

import com.ecoapp.data.model.EcoItem
import com.ecoapp.domain.repository.EcoRepository
import javax.inject.Inject

class FetchEcoDataUseCaseImpl @Inject constructor(
    private val ecoRepository: EcoRepository
) : FetchEcoDataUseCase {
    override suspend fun execute(): List<EcoItem> {
        return ecoRepository.getEcoData()
    }
}