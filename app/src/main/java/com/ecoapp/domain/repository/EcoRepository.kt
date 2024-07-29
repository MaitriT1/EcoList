package com.ecoapp.domain.repository

import com.ecoapp.data.model.EcoItem

interface EcoRepository {
    fun getEcoData(): List<EcoItem>
}
