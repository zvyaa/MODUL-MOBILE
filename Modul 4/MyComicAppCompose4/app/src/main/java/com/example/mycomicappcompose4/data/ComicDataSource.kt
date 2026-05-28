package com.example.mycomicappcompose4.data

import android.content.Context
import com.example.mycomicappcompose.R

object ComicDataSource {
    fun getComicList(context: Context) = listOf(
        Comic(1, "Like Mother Like Daughter", "Thriller", "Carnby Kim", "2023",
            context.getString(R.string.synopsis_like_mother),
            R.drawable.lmld,
            "https://www.webtoons.com/id/thriller/like-mother-like-daughter/list?title_no=3622"),
        Comic(2, "The Real Lesson", "Action", "Hyun", "2022",
            context.getString(R.string.synopsis_real_lesson),
            R.drawable.thereallesson,
            "https://www.webtoons.com/id/action/the-real-lesson/list?title_no=2423"),
        Comic(3, "Wee!", "Slice of Life", "Soonkki", "2023",
            context.getString(R.string.synopsis_wee),
            R.drawable.wee,
            "https://www.webtoons.com/id/slice-of-life/wee/list?title_no=3085"),
        Comic(4, "The Villainess Reverses the Hourglass", "Romantic Fantasy", "Sansobee", "2021",
            context.getString(R.string.synopsis_villainess),
            R.drawable.villainess,
            "https://www.webtoons.com/id/romantic-fantasy/the-villainess-reverses-hourglass/list?title_no=9517"),
        Comic(5, "The Fantasie of a Stepmother", "Romantic Fantasy", "Rukyung", "2020",
            context.getString(R.string.synopsis_stepmother),
            R.drawable.stepmother,
            "https://www.webtoons.com/id/romantic-fantasy/the-fantasie-of-a-stepmother/list?title_no=9773")
    )
}