package com.example.marvelsuperheros.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Result
import com.example.domain.model.SuperHeroModel
import com.example.domain.usecase.AddHeroUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.HttpUrl

class AddSuperHeroViewModel constructor(private val addHeroUseCase: AddHeroUseCase): ViewModel() {
    val name = MutableLiveData<String>()
    val url = MutableLiveData<String>()
    val superpower = MutableLiveData<String>()

    private val viewState: MutableLiveData<ViewState> = MutableLiveData()

    val viewStateObservable: LiveData<ViewState> = viewState

    private val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        viewState.value = ViewState.Loading(false)
        viewState.value = ViewState.Error(throwable?.message ?: "There was some error")
    }
    private val context = coroutineExceptionHandler

    fun onSubmit(){
        if(validate(name.value, url.value, superpower.value)){
            viewModelScope.launch(context){
                addHeroUseCase.addHero(SuperHeroModel(name = name.value!!, image = url.value!!, description = superpower.value!!)).collect { result ->
                    when(result){
                        Result.Loading -> viewState.value = ViewState.Loading(true)
                        is Result.Error -> {
                            viewState.value = ViewState.Loading(false)
                            viewState.value = ViewState.Error(result.exception.message ?: "There was an error fetching heroes.")
                        }
                        is Result.Success -> {
                            viewState.value = ViewState.Loading(false)
                            viewState.value = ViewState.SuperHeroAdded
                        }
                    }
                }
            }
        }
    }

    private fun validate(name: String?, url: String?, superpower: String?): Boolean {
        var valid = true
        if(name.isNullOrBlank() || name.length < 5) {
            valid = false
            viewState.value = ViewState.InvalidName("Name must be at least 5 character.")
        }
        if(url.isNullOrBlank() || HttpUrl.parse(url) == null) {
            valid = false
            viewState.value = ViewState.InvalidProfilePictureUrl("Invalid URL for profile picture.")
        }
        if(superpower.isNullOrBlank() ||  superpower.length < 20) {
            valid = false
            viewState.value = ViewState.InvalidSuperpowers("Superpowers must be at least 20 character.")
        }
        return valid
    }

    sealed class ViewState{
        data class Loading(val isLoading: Boolean = false): ViewState()
        object SuperHeroAdded: ViewState()
        data class Error(val message: String): ViewState()
        data class InvalidName(val message: String): ViewState()
        data class InvalidProfilePictureUrl(val message: String): ViewState()
        data class InvalidSuperpowers(val message: String): ViewState()
    }
}