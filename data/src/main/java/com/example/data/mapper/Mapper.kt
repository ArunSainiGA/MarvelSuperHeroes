package com.example.data.mapper

interface Mapper<I, O> {
    fun toOutput(input: I): O
    fun toInput(output: O): I
}