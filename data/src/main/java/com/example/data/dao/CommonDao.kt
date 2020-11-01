package com.example.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.data.entity.SuperHeroEntity
import kotlinx.coroutines.flow.Flow

interface CommonDao<T> {
    @Insert
    suspend fun insert(entity: T)

    @Insert
    suspend fun insertAll(entities: List<T>)

    @Delete
    suspend fun delete(entity: T)

    @Delete
    suspend fun deleteAll(entities: List<T>)
}