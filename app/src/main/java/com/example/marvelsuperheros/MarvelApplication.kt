package com.example.marvelsuperheros

import android.app.Application
import androidx.multidex.MultiDexApplication

class MarvelApplication: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        DependencyInjector.init(this)
    }
}