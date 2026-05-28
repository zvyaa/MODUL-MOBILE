package com.example.mycomicappxmlm4.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ComicViewModelFactory(
    private val appName: String,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComicViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ComicViewModel(appName, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}