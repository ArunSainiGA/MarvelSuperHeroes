package com.example.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.data.utils.*
import java.util.*

@Entity(tableName = ENTITY_TABLE_NAME_SUPER_HERO, indices = arrayOf(Index(value = [ENTITY_KEY_SUPER_HERO_NAME], unique = true)))
data class SuperHeroEntity (
    @PrimaryKey
    @ColumnInfo(name = ENTITY_KEY_SUPER_HERO_ID) val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = ENTITY_KEY_SUPER_HERO_NAME) val name: String,
    @ColumnInfo(name = ENTITY_KEY_SUPER_HERO_DESCRIPTION) val description: String,
    @ColumnInfo(name = ENTITY_KEY_SUPER_HERO_PROFILE_PICTURE) val image: String
)