package com.example.mycomicappxml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mycomicappxml.data.ComicDataSource
import com.example.mycomicappxml.databinding.FragmentComicDetailBinding

class ComicDetailFragment : Fragment() {

    private var _binding: FragmentComicDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComicDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val comicId = arguments?.getInt("comicId") ?: return
        val comic = ComicDataSource.getComicList(requireContext()).find { it.id == comicId }

        comic?.let {
            binding.imgDetailCover.setImageResource(it.imageResId)
            binding.tvDetailTitle.text = it.title
            binding.tvDetailGenre.text = getString(R.string.label_genre) + it.genre
            binding.tvDetailAuthor.text = getString(R.string.label_author) + it.author
            binding.tvDetailYear.text = getString(R.string.label_year) + it.year
            binding.tvDetailSynopsis.text = it.synopsis
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}