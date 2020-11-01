package com.example.data.repository

import com.example.data.datasource.local.LocalSuperHeroDataSource
import com.example.data.mapper.SuperHeroEntityMapper
import com.example.domain.model.SuperHeroModel
import com.example.domain.repository.SuperHeroesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SuperHeroesRepositoryImpl constructor(private val localDataSource: LocalSuperHeroDataSource, private val mapper: SuperHeroEntityMapper): SuperHeroesRepository {
    override suspend fun addSuperHero(hero: SuperHeroModel) =
        localDataSource.addEntity(mapper.toInput(hero))

    override suspend fun retireSuperHero(hero: SuperHeroModel) =
        localDataSource.deleteEntity(mapper.toInput(hero))

    override suspend fun retireSuperHero(id: String) =
        localDataSource.deleteEntity(id)

    override suspend fun fetchAllSuperHeroes(): List<SuperHeroModel> =
        localDataSource.fetchAllEntity().map { entity -> mapper.toOutput(entity) }
}