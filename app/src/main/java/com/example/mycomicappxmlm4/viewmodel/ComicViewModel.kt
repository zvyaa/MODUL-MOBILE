package com.example.mycomicappxmlm4.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mycomicappxmlm4.data.Comic
import com.example.mycomicappxmlm4.data.ComicDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class ComicViewModel(private val appName: String, private val context: Context) : ViewModel() {

    private val _comicList = MutableStateFlow<List<Comic>>(emptyList())
    val comicList: StateFlow<List<Comic>> = _comicList

    private val _selectedComic = MutableStateFlow<Comic?>(null)
    val selectedComic: StateFlow<Comic?> = _selectedComic

    init {
        loadComics()
    }

    private fun loadComics() {
        val comics = ComicDataSource.getComicList(context)
        _comicList.value = comics
        Timber.d("[$appName] ${comics.size} item comic berhasil dimuat ke dalam list")
    }

    fun selectComic(comic: Comic) {
        _selectedComic.value = comic
        Timber.d("[$appName] Comic dipilih: ${comic.title}")
    }

    fun onDetailClicked(comic: Comic) {
        Timber.d("[$appName] Tombol Detail ditekan untuk: ${comic.title}")
        selectComic(comic)
    }

    fun onExplicitIntentClicked(comic: Comic) {
        Timber.d("[$appName] Tombol Explicit Intent ditekan untuk: ${comic.title}")
    }
}