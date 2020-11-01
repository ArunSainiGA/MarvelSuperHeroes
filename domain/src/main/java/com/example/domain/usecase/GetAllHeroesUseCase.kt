package com.example.domain.usecase

import com.example.domain.Result
import com.example.domain.model.SuperHeroModel
import com.example.domain.repository.SuperHeroesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetAllHeroesUseCase constructor(private val repository: SuperHeroesRepository) {
    suspend fun getHeroes(): Flow<Result<List<SuperHeroModel>>> = flow{
        emit(Result.Loading)
        try {
            emit(Result.Success(repository.fetchAllSuperHeroes()))
        }catch (ex: Exception){
            emit(Result.Error(ex))
        }
    }
}