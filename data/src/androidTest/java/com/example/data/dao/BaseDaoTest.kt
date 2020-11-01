package com.example.data.dao

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.data.MarvelDB
import org.junit.After
import org.junit.Before

open class BaseDaoTest {
    protected lateinit var db: MarvelDB
    protected lateinit var superHeroDao: SuperHeroDao

    @Before fun createDB(){
        val context = InstrumentationRegistry.getInstrumentation().context
        db = Room.inMemoryDatabaseBuilder(context, MarvelDB::class.java).build()

        superHeroDao = db.superHeroDao()
    }

    @After fun cleanUp(){
        db.close()
    }
}