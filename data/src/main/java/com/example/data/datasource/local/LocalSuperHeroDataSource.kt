package com.example.data.datasource.local

import com.example.data.dao.SuperHeroDao
import com.example.data.entity.SuperHeroEntity
import kotlinx.coroutines.flow.Flow

class LocalSuperHeroDataSource constructor(private val dao: SuperHeroDao) {

    suspend fun addEntity(hero: SuperHeroEntity) = dao.insert(hero)

    suspend fun deleteEntity(hero: SuperHeroEntity) = dao.delete(hero)

    suspend fun deleteEntity(id: String) = dao.delete(id)

    suspend fun fetchAllEntity(): List<SuperHeroEntity> = dao.fetchAll()
}