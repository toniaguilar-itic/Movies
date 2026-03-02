package cat.iticbcn.myapplication.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.iticbcn.myapplication.data.network.MovieApiService
import cat.iticbcn.myapplication.core.RetrofitHelper
import cat.iticbcn.myapplication.data.model.Movie
import cat.iticbcn.myapplication.domain.usecases.GetMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.HttpUrl

class MainViewModel : ViewModel() {

    private val retrofit = RetrofitHelper.getRetrofit()

    private val api = retrofit.create(MovieApiService::class.java)

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private val _selectedMovies = MutableStateFlow<List<Movie>>(emptyList())
    val selectedMovies: StateFlow<List<Movie>> = _selectedMovies

    private val _selectedEndpoints = MutableStateFlow<Set<String>>(emptySet())
    val selectedEndpoints: StateFlow<Set<String>> = _selectedEndpoints

    private val _query = MutableStateFlow("")

    val getMoviesUseCase = GetMoviesUseCase()
    val isLoading = MutableLiveData<Boolean>()

 /*
        viewModelScope.launch {
            isLoading.postValue(true)
            val result : List<Movie> ? = getMoviesUseCase()
            // Li donem feed-back a l'usuari informant que està carregant mentre
            // busca les dades amb un progress-bar
            //Haurem de tenir creat el component a l'activity
            if (!result.isNullOrEmpty()) {
                isLoading.postValue(false)
            }
        }
    }
*/
    fun updateQuery(query: String) {
        _query.value = query
        filterMovies()
    }

    fun loadMovies(selection: String) {
        viewModelScope.launch {
            try {
                isLoading.postValue(true)
                val retrievedMovies = GetMoviesUseCase().invoke(selection)


                    val updatedList = _movies.value.toMutableList()
                    updatedList.addAll(retrievedMovies)
                    updatedList.sortBy { it.title }
                    _movies.value = updatedList
                    filterMovies()

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun removeMovies(selection: String) {
        viewModelScope.launch {
            try {
                val response = api.getAllMovies(selection)
                if (response.isSuccessful) {
                    val retrievedMovies = response.body().orEmpty()
                    val updatedList = _movies.value.toMutableList()
                    updatedList.removeAll(retrievedMovies.toSet())
                    _movies.value = updatedList
                    filterMovies()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun filterMovies() {
        val query = _query.value
        _selectedMovies.value = _movies.value.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }

    fun toggleEndpoint(endpoint: String) {
        val current = _selectedEndpoints.value.toMutableSet()

        if (current.contains(endpoint)) {
            current.remove(endpoint)
            removeMovies("$endpoint/")
        } else {
            current.add(endpoint)
            loadMovies("$endpoint/")
        }

        _selectedEndpoints.value = current
    }


}