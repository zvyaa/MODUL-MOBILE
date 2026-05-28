package com.example.mycomicappxml

import androidx.lifecycle.ViewModel
import com.example.mycomicappxml.data.Comic

class ComicViewModel : ViewModel() {
    val comicList = listOf(
        Comic(
            1,
            "Like Mother, Like Daughter",
            "2022",
            "Thriller",
            "Carnby Kim",
            "Seorang ibu dan anak perempuannya menyimpan rahasia gelap yang saling terkait. Ketika masa lalu mulai terungkap, batas antara korban dan pelaku semakin kabur.",
            R.drawable.lmld,
            "https://www.webtoons.com/id/thriller/like-mother-like-daughter/list?title_no=3622"
        ),
        Comic(
            2,
            "The Real Lesson",
            "2021",
            "Aksi",
            "Hyun",
            "Seorang mantan petarung jalanan menjadi guru di sekolah yang penuh kekerasan. Ia harus membuktikan bahwa pelajaran sesungguhnya bukan hanya soal nilai akademis.",
            R.drawable.thereallesson,
            "https://www.webtoons.com/id/action/the-real-lesson/list?title_no=2423"
        ),
        Comic(
            3,
            "Wee",
            "2022",
            "Kehidupan Sehari-hari",
            "Soonkki",
            "Kisah kehidupan sehari-hari yang manis dan hangat tentang persahabatan, cinta, dan menemukan kebahagiaan dalam hal-hal kecil di sekitar kita.",
            R.drawable.wee,
            "https://www.webtoons.com/id/slice-of-life/wee/list?title_no=3085"
        ),
        Comic(
            4,
            "The Villainess Reverses the Hourglass",
            "2020",
            "Fantasi Romantis",
            "Sansobee",
            "Seorang penjahat wanita mendapat kesempatan kedua untuk mengubah takdirnya. Dengan kecerdasan dan keberaniannya, ia membalikkan nasib yang telah ditentukan.",
            R.drawable.villainess,
            "https://www.webtoons.com/id/romantic-fantasy/the-villainess-reverses-hourglass/list?title_no=9517"
        ),
        Comic(
            5,
            "The Fantasie of a Stepmother",
            "2021",
            "Fantasi Romantis",
            "Dollargrain",
            "Seorang ibu tiri yang dingin dan kejam mendapat kesempatan untuk mengulang hidupnya. Kali ini, ia memilih untuk melindungi anak tirinya dari bahaya yang mengancam.",
            R.drawable.stepmother,
            "https://www.webtoons.com/id/romantic-fantasy/the-fantasie-of-a-stepmother/list?title_no=9773"
        )
    )
}