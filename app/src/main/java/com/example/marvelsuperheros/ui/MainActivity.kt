package com.example.marvelsuperheros.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelsuperheros.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, SuperHeroesFragment.createInstance())
        transaction.commit()
    }
}