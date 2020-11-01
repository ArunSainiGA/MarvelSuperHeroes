package com.example.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.entity.SuperHeroEntity
import com.example.data.utils.ENTITY_KEY_SUPER_HERO_ID
import com.example.data.utils.ENTITY_TABLE_NAME_SUPER_HERO
import kotlinx.coroutines.flow.Flow

@Dao
interface SuperHeroDao: CommonDao<SuperHeroEntity> {

    @Query("SELECT COUNT(*) FROM $ENTITY_TABLE_NAME_SUPER_HERO")
    suspend fun count(): Int

    @Query("SELECT * FROM $ENTITY_TABLE_NAME_SUPER_HERO")
    suspend fun fetchAll(): List<SuperHeroEntity>

    @Query("DELETE FROM $ENTITY_TABLE_NAME_SUPER_HERO WHERE $ENTITY_KEY_SUPER_HERO_ID = :id")
    suspend fun delete(id: String)
}