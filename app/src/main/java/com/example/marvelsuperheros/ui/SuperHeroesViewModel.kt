package com.example.marvelsuperheros.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Result
import com.example.domain.model.SuperHeroModel
import com.example.domain.usecase.DeleteHeroUseCase
import com.example.domain.usecase.GetAllHeroesUseCase
import com.example.marvelsuperheros.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SuperHeroesViewModel constructor(
    private val getAllHeroesUseCase: GetAllHeroesUseCase,
    private val deleteHeroUseCase: DeleteHeroUseCase
): ViewModel() {
    private val viewState: MutableLiveData<ViewState> = MutableLiveData()
    private val actions: SingleLiveEvent<ViewActions> = SingleLiveEvent()

    val viewStateObservable: LiveData<ViewState> = viewState
    val viewActionsObservable: LiveData<ViewActions> = actions

    private val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        viewState.value = ViewState.Loading(false)
        viewState.value = ViewState.Error(throwable?.message ?: "There was some error")
    }
    private val context = coroutineExceptionHandler

    init {
        fetchHeroes()
    }

    fun fetchHeroes(){
        // Fetching all super heroes present in local storage
        viewModelScope.launch(context){
            getAllHeroesUseCase.getHeroes().collect { result ->
                when(result){
                    Result.Loading -> viewState.value = ViewState.Loading(true)
                    is Result.Error -> {
                        viewState.value = ViewState.Loading(false)
                        viewState.value = ViewState.Error(result.exception.message ?: "There was an error fetching heroes.")
                    }
                    is Result.Success -> {
                        viewState.value = ViewState.Loading(false)
                        viewState.value = ViewState.SuperHeroLoaded(result.data)
                    }
                }
            }
        }
    }

    fun removeSuperHero(item: SuperHeroModel, position: Int){
        viewModelScope.launch(context){
            deleteHeroUseCase.deleteHero(item.id).collect { result ->
                when(result){
                    Result.Loading -> viewState.value = ViewState.Loading(true)
                    is Result.Error -> {
                        viewState.value = ViewState.Loading(false)
                        viewState.value = ViewState.Error(result.exception.message ?: "There was an error fetching heroes.")
                    }
                    is Result.Success -> {
                        viewState.value = ViewState.Loading(false)
                        viewState.value = ViewState.HeroDeleted(position)
                    }
                }
            }
        }
    }

    fun addHeroClicked(){
        actions.value = ViewActions.AddHero
    }

    sealed class ViewState{
        data class Loading(val isLoading: Boolean = false): ViewState()
        data class SuperHeroLoaded(val data: List<SuperHeroModel>): ViewState()
        data class HeroDeleted(val position: Int): ViewState()
        data class Error(val message: String): ViewState()
    }

    sealed class ViewActions{
        object AddHero: ViewActions()
    }
}