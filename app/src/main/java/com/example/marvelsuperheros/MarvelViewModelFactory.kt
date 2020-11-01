package com.example.marvelsuperheros

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MarvelViewModelFactory<T>(val objectCreator: () -> T): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return objectCreator() as T
    }
}