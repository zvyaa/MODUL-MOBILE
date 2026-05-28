package com.example.mycomicappcompose4

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mycomicappcompose4.data.Comic
import com.example.mycomicappcompose4.data.ComicDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class ComicViewModel(private val appName: String) : ViewModel() {

    private val _comicList = MutableStateFlow<List<Comic>>(emptyList())
    val comicList: StateFlow<List<Comic>> = _comicList

    private val _selectedComicId = MutableStateFlow<Int?>(null)
    val selectedComicId: StateFlow<Int?> = _selectedComicId

    private val _openUrlEvent = MutableStateFlow<String?>(null)
    val openUrlEvent: StateFlow<String?> = _openUrlEvent

    fun loadComics(context: Context) {
        val list = ComicDataSource.getComicList(context)
        _comicList.value = list
        Timber.d("[$appName] Data loaded: ${list.size} comics masuk ke dalam list")
        list.forEach { comic ->
            Timber.d("[$appName] Item: ${comic.title} | ${comic.genre} | ${comic.year}")
        }
    }

    fun onDetailClicked(comicId: Int) {
        val comic = _comicList.value.find { it.id == comicId }
        Timber.d("[$appName] Tombol Detail ditekan → ${comic?.title} | Genre: ${comic?.genre}")
        _selectedComicId.value = comicId
    }

    fun onReadClicked(url: String, title: String) {
        Timber.d("[$appName] Tombol Read ditekan → URL: $url | Judul: $title")
        _openUrlEvent.value = url
    }

    fun onUrlOpened() {
        _openUrlEvent.value = null
    }

    fun onNavigatedToDetail(comicId: Int) {
        val comic = _comicList.value.find { it.id == comicId }
        Timber.d("[$appName] Berpindah ke Detail → ${comic?.title} | Author: ${comic?.author} | Year: ${comic?.year}")
        _selectedComicId.value = null
    }
}