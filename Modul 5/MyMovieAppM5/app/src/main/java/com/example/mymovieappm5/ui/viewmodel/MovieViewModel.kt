package com.example.mymovieappm5.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieappm5.data.repository.MovieRepository
import com.example.mymovieappm5.domain.model.Movie
import com.example.mymovieappm5.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _popularMovies = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())
    val popularMovies: StateFlow<Resource<List<Movie>>> = _popularMovies

    private val _nowPlayingMovies = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())
    val nowPlayingMovies: StateFlow<Resource<List<Movie>>> = _nowPlayingMovies

    private val _searchResults = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())
    val searchResults: StateFlow<Resource<List<Movie>>> = _searchResults

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        getPopularMovies()
        getNowPlayingMovies()
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            repository.getPopularMovies().collect {
                _popularMovies.value = it
            }
        }
    }

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            repository.getNowPlayingMovies().collect {
                _nowPlayingMovies.value = it
            }
        }
    }

    fun searchMovies(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            _searchResults.value = Resource.Loading()
            _searchResults.value = repository.searchMovies(query)
        }
    }
}