package es.dam.psp.marvelapp.features.superheroes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.dam.psp.marvelapp.features.superheroes.domain.GetSuperHeroesUseCase
import es.dam.psp.marvelapp.features.superheroes.domain.SuperHero
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuperHeroListViewModel(
    private val getSuperHeroesUseCase: GetSuperHeroesUseCase ) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    fun loadSuperHeroes() {
            //1. Mostramos el loading.
            _uiState.value = UiState(hasError = true)

         //   delay(2000) // espera 2 segundos sin bloquear la pantalla

            //2. Mostramos datos
            _uiState.value = UiState(
               superHeroes = getSuperHeroesUseCase.invoke()
            )

    }

    //la foto de la pantalla
    data class UiState(
        val isLoading: Boolean = false,
        val hasError: Boolean = false,
        val superHeroes: List<SuperHero>? = null,
    )

    companion object {
        fun factory(getSuperHeroesUseCase: GetSuperHeroesUseCase ) = viewModelFactory {
            initializer { SuperHeroListViewModel(getSuperHeroesUseCase) }
        }
    }
}