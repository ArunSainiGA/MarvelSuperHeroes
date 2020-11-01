package com.example.domain.usecase

import com.example.domain.Result
import com.example.domain.model.SuperHeroModel
import com.example.domain.repository.SuperHeroesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class AddHeroUseCase constructor(private val repository: SuperHeroesRepository) {
    suspend fun addHero(hero: SuperHeroModel): Flow<Result<Unit>>  = flow{
        emit(Result.Loading)
        try {
            repository.addSuperHero(hero)
            emit(Result.Success(Unit))
        }catch (ex: Exception){
            emit(Result.Error(ex))
        }
    }.flowOn(Dispatchers.IO)
}