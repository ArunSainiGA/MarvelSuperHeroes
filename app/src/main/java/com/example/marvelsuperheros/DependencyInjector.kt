package com.example.marvelsuperheros

import android.app.Application
import com.example.data.MarvelDB
import com.example.data.datasource.local.LocalSuperHeroDataSource
import com.example.data.mapper.SuperHeroEntityMapper
import com.example.data.repository.SuperHeroesRepositoryImpl
import com.example.domain.repository.SuperHeroesRepository
import com.example.domain.usecase.AddHeroUseCase
import com.example.domain.usecase.DeleteHeroUseCase
import com.example.domain.usecase.GetAllHeroesUseCase

object DependencyInjector{

    private lateinit var marvelDB: MarvelDB

    fun init(application: Application){
        marvelDB = MarvelDB.getInstance(application)
    }

    private fun getSuperHeroRepo() = SuperHeroesRepositoryImpl(LocalSuperHeroDataSource(marvelDB.superHeroDao()), SuperHeroEntityMapper()) as SuperHeroesRepository

    fun getAddHeroUseCase() = AddHeroUseCase(getSuperHeroRepo())

    fun getDeleteHeroUseCase() = DeleteHeroUseCase(getSuperHeroRepo())

    fun getAllHeroUseCase() = GetAllHeroesUseCase(getSuperHeroRepo())

    /*companion object{
        private lateinit var instance:  DependencyInjector

        *//*@Throws(IllegalStateException::class)
        fun getInstance(): DependencyInjector{
            return if(!this::instance.isInitialized)
                instance
            else
                throw IllegalStateException("Dependency Injector has not been initialized yet.")
        }*//*
//        @Throws(IllegalStateException::class)
        fun getInstance(application: Application): DependencyInjector{
            synchronized(this){
                if(!this::instance.isInitialized)
                    instance = DependencyInjector(application)
            }
            return instance
        }

        *//*fun init(application: Application){
            synchronized(this){
                if(!this::instance.isInitialized)
                    instance = DependencyInjector(application)
            }
        }*//*
    }*/
}