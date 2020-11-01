package com.example.domain.usecase

import com.example.domain.Result
import com.example.domain.repository.SuperHeroesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class DeleteHeroUseCase constructor(private val repository: SuperHeroesRepository) {
    suspend fun deleteHero(id: String): Flow<Result<Unit>> = flow{
        emit(Result.Loading)
        try {
            repository.retireSuperHero(id)
            emit(Result.Success(Unit))
        }catch (ex: Exception){
            emit(Result.Error(ex))
        }
    }
}