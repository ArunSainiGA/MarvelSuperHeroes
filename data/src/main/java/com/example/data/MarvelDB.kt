package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.dao.SuperHeroDao
import com.example.data.entity.SuperHeroEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@Database(entities = arrayOf(SuperHeroEntity::class), version = 1, exportSchema = false)
abstract class MarvelDB: RoomDatabase(){
    abstract fun superHeroDao(): SuperHeroDao

    companion object{
        private lateinit var instance: MarvelDB

        fun getInstance(context: Context): MarvelDB{
            synchronized(this){
                if(!this::instance.isInitialized)
                    instance = Room.databaseBuilder(context, MarvelDB::class.java, "MarvelDatabase").build()
                instance.prepopulate()
                return instance
            }
        }
    }

    private fun prepopulate(){
        CoroutineScope(Dispatchers.IO).launch {
            superHeroDao().count().let { count ->
                if(count == 0){
                    superHeroDao().insertAll(getPrePopulationHeroes())
                }
            }
        }
    }

    private fun getPrePopulationHeroes() =
        mutableListOf<SuperHeroEntity>().apply {
            add(SuperHeroEntity(
                name = "Iron Man", description = "Genius-level intellect, multiple powered armor suits, ability to fly.", image = "https://m.media-amazon.com/images/M/MV5BMTk1MDg3MTY1Ml5BMl5BanBnXkFtZTcwOTMyMTU0OQ@@._V1_CR0,60,640,360_AL_UX477_CR0,0,477,268_AL_.jpg"
            ))

            add(SuperHeroEntity(
                name = "Captain America", description = "Strength, agility, stamina, healing ability, expert tactician, martial artist, indestructible shield.", image = "https://m.media-amazon.com/images/M/MV5BNTM2ZjRhMGMtZjM1OC00OTM5LTkxMjQtOTBmYjE4NTI4YTVhXkEyXkFqcGdeQW1yb3NzZXI@._V1_CR122,0,1259,708_AL_UY268_CR46,0,477,268_AL_.jpg"
            ))

            add(SuperHeroEntity(
                name = "Vision", description = "Being an artificial life-form/android of sorts, Vision has superhuman senses, superhuman stamina, reflexes, speed, agility, strength, superhuman analytical capabilities, and the ability to process information and make calculations with superhuman speed and accuracy.", image = "https://www.awn.com/sites/default/files/styles/original/public/image/attached/1024624-bov6670v506.1052-final.jpg?itok=-stNZWXu"
            ))

            add(SuperHeroEntity(
                name = "Wanda", description = "Wanda is also a class 5 most powerful mutant in the MCU. Wanda possesses the incredible power of Chaos Magic. Wanda's power can warp reality and existence and bring total destruction to the cosmos.", image = "https://i2.wp.com/www.nerdsandbeyond.com/wp-content/uploads/2019/02/MV5BMzdiNWU4NzUtYWRjYi00NjI3LWJmODItZDYxZTFhNTRmYTBiXkEyXkFqcGdeQXVyNzE5MDQ4MTc@._V1_.jpg"
            ))

            add(SuperHeroEntity(
                name = "Spider Man", description = "Strength, jumping, leaping, speed, “danger sense” precognition, custom web-shooters.", image = "https://m.media-amazon.com/images/M/MV5BZjM2MGE2OTYtNDM5Ni00NmVlLTk4ZjAtNTIxMWRkOTQ5NzNjXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_UX477_CR0,0,477,268_AL_.jpg"
            ))

            add(SuperHeroEntity(
                name = "Hawk Eye", description = "Exceptional fencer, acrobat and marksman, having been trained from childhood in the circus and by the criminals Trick Shot and Swordsman", image = "https://imgix.bustle.com/uploads/image/2018/4/25/4c8a245e-4e6a-443e-b5ea-381b153ace34-screen-shot-2018-04-25-at-41413-pm.png?w=1200&h=630&q=70&fit=crop&crop=faces&fm=jpg"
            ))

            add(SuperHeroEntity(
                name = "Black Widow", description = "Expert tactician, hand-to-hand combatant and secret agent Slowed aging, and enhanced immune system.", image = "https://m.media-amazon.com/images/M/MV5BMWNiMWJlNWQtMjM0OS00OTVlLWE0ZDUtYzQ3MGQ3N2U0MzFkXkEyXkFqcGdeQWFybm8@._V1_CR228,0,1321,743_AL_UY268_CR82,0,477,268_AL_.jpg"
            ))

            add(SuperHeroEntity(
                name = "Thor", description = "Strength, speed, stamina, durability, weather manipulation, flight (via Mjolnir), dense skin and bones with a resistance to injury.", image = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTO_sOnpV3m9QzwhSMxwrT-Poh4wtJniFzuJA&usqp=CAU"
            ))

            add(SuperHeroEntity(
                name = "Ant Man", description = "Genius-level intellect, size-changing via Pym Particles, telepathic communication with insects, dimension-hopping.", image = "https://m.media-amazon.com/images/M/MV5BNzY3ODg5ZWUtOWZjYi00NGY5LTlhYWEtN2IwMjUxM2I2NzJjXkEyXkFqcGdeQW1yb3NzZXI@._V1_UX477_CR0,0,477,268_AL_.jpg"
            ))

            add(SuperHeroEntity(
                name = "Captain Marvel", description = "Strength, speed, stamina, resistant to most toxins, energy absorption and manipulation.", image = "https://m.media-amazon.com/images/M/MV5BODlhOGM4MGMtYjBjMi00MWQ2LTk4OWUtYzQ2YzE0NWZkODlkXkEyXkFqcGdeQW1yb3NzZXI@._V1_CR251,0,1419,798_AL_UY268_CR84,0,477,268_AL_.jpg"
            ))
        }

}