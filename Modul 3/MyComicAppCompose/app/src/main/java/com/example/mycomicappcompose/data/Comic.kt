package com.example.mycomicappcompose.data

data class Comic(
    val id: Int,
    val title: String,
    val genre: String,
    val author: String,
    val year: String,
    val synopsis: String,
    val imageResId: Int,
    val webtoonUrl: String
)