package com.example.domain

import kotlin.Exception

sealed class Result<out D>{
    data class Success<out D>(val data: D): Result<D>()
    data class Error(val exception: Exception): Result<Nothing>()
    object Loading: Result<Nothing>()
}