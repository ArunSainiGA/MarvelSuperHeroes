package com.example.domain.repository

import com.example.domain.model.SuperHeroModel
import kotlinx.coroutines.flow.Flow

interface SuperHeroesRepository {
    suspend fun addSuperHero(hero: SuperHeroModel)
    suspend fun retireSuperHero(hero: SuperHeroModel)
    suspend fun retireSuperHero(id: String)
    suspend fun fetchAllSuperHeroes(): List<SuperHeroModel>
}