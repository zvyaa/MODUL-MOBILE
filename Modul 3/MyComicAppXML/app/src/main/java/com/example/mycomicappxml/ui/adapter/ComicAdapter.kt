package com.example.mycomicappxml

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.mycomicappxml.data.Comic
import com.example.mycomicappxml.databinding.ItemComicBinding

class ComicAdapter(
    private val comics: List<Comic>,
    private val onDetailClick: (Int) -> Unit
) : RecyclerView.Adapter<ComicAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemComicBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemComicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comic = comics[position]
        val context = holder.itemView.context
        with(holder.binding) {
            imgComicCover.setImageResource(comic.imageResId)
            tvComicTitle.text = comic.title
            tvComicYear.text = comic.year
            tvComicGenre.text = context.getString(R.string.label_genre) + comic.genre
            tvComicAuthor.text = comic.author

            btnRead.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, comic.webtoonUrl.toUri())
                context.startActivity(intent)
            }

            btnDetail.setOnClickListener {
                onDetailClick(comic.id)
            }
        }
    }

    override fun getItemCount() = comics.size
}