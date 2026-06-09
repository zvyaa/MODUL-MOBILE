package com.example.mymovieappm5.data.repository

import com.example.mymovieappm5.data.local.MovieDatabase
import com.example.mymovieappm5.data.local.MovieEntity
import com.example.mymovieappm5.data.remote.RetrofitInstance
import com.example.mymovieappm5.domain.model.Movie
import com.example.mymovieappm5.util.Constants
import com.example.mymovieappm5.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepository(private val database: MovieDatabase) {

    fun getPopularMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        try {
            val cacheTime = database.movieDao().getCacheTime(Constants.CATEGORY_POPULAR)
            val isCacheValid = cacheTime != null &&
                    System.currentTimeMillis() - cacheTime < Constants.CACHE_DURATION

            if (isCacheValid) {
                database.movieDao().getMoviesByCategory(Constants.CATEGORY_POPULAR)
                    .collect { entities ->
                        emit(Resource.Success(entities.map { it.toMovie() }))
                    }
            } else {
                val response = RetrofitInstance.api.getPopularMovies(Constants.API_KEY)
                val entities = response.results.map {
                    MovieEntity(
                        id = it.id,
                        title = it.title,
                        overview = it.overview,
                        posterPath = it.posterPath,
                        backdropPath = it.backdropPath,
                        releaseDate = it.releaseDate,
                        voteAverage = it.voteAverage,
                        voteCount = it.voteCount,
                        popularity = it.popularity,
                        category = Constants.CATEGORY_POPULAR
                    )
                }
                database.movieDao().deleteMoviesByCategory(Constants.CATEGORY_POPULAR)
                database.movieDao().insertMovies(entities)
                database.movieDao().getMoviesByCategory(Constants.CATEGORY_POPULAR)
                    .collect { cached ->
                        emit(Resource.Success(cached.map { it.toMovie() }))
                    }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Terjadi kesalahan"))
        }
    }

    fun getNowPlayingMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        try {
            val cacheTime = database.movieDao().getCacheTime(Constants.CATEGORY_NOW_PLAYING)
            val isCacheValid = cacheTime != null &&
                    System.currentTimeMillis() - cacheTime < Constants.CACHE_DURATION

            if (isCacheValid) {
                database.movieDao().getMoviesByCategory(Constants.CATEGORY_NOW_PLAYING)
                    .collect { entities ->
                        emit(Resource.Success(entities.map { it.toMovie() }))
                    }
            } else {
                val response = RetrofitInstance.api.getNowPlayingMovies(Constants.API_KEY)
                val entities = response.results.map {
                    MovieEntity(
                        id = it.id,
                        title = it.title,
                        overview = it.overview,
                        posterPath = it.posterPath,
                        backdropPath = it.backdropPath,
                        releaseDate = it.releaseDate,
                        voteAverage = it.voteAverage,
                        voteCount = it.voteCount,
                        popularity = it.popularity,
                        category = Constants.CATEGORY_NOW_PLAYING
                    )
                }
                database.movieDao().deleteMoviesByCategory(Constants.CATEGORY_NOW_PLAYING)
                database.movieDao().insertMovies(entities)
                database.movieDao().getMoviesByCategory(Constants.CATEGORY_NOW_PLAYING)
                    .collect { cached ->
                        emit(Resource.Success(cached.map { it.toMovie() }))
                    }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Terjadi kesalahan"))
        }
    }

    suspend fun searchMovies(query: String): Resource<List<Movie>> {
        return try {
            val response = RetrofitInstance.api.searchMovies(Constants.API_KEY, query)
            Resource.Success(response.results.map { it ->
                Movie(
                    id = it.id,
                    title = it.title,
                    overview = it.overview,
                    posterPath = it.posterPath,
                    backdropPath = it.backdropPath,
                    releaseDate = it.releaseDate,
                    voteAverage = it.voteAverage,
                    voteCount = it.voteCount,
                    popularity = it.popularity
                )
            })
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Terjadi kesalahan")
        }
    }

    private fun MovieEntity.toMovie() = Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        popularity = popularity
    )
}