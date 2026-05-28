package com.example.mycomicappxmlm4.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycomicappxmlm4.data.Comic
import com.example.mycomicappxmlm4.databinding.ItemComicHighlightBinding

class ComicHighlightAdapter(
    private val comics: List<Comic>,
    private val onDetailClick: (Int) -> Unit
) : RecyclerView.Adapter<ComicHighlightAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemComicHighlightBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemComicHighlightBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comic = comics[position]
        with(holder.binding) {
            imgHighlightCover.setImageResource(comic.imageResId)
            tvHighlightTitle.text = comic.title
            tvHighlightGenre.text = comic.genre

            btnHighlightDetail.setOnClickListener {
                onDetailClick(comic.id)
            }
        }
    }

    override fun getItemCount() = comics.size
}