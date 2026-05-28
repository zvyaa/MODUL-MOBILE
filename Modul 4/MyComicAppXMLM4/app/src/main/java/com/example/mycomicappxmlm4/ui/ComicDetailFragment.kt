package com.example.mycomicappxmlm4.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mycomicappxmlm4.R
import com.example.mycomicappxmlm4.databinding.FragmentComicDetailBinding
import com.example.mycomicappxmlm4.viewmodel.ComicViewModel
import com.example.mycomicappxmlm4.viewmodel.ComicViewModelFactory
import kotlinx.coroutines.launch
import timber.log.Timber

class ComicDetailFragment : Fragment() {

    private var _binding: FragmentComicDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ComicViewModel by viewModels {
        ComicViewModelFactory("MyComicAppXMLM4", requireContext())
    }

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.comicList.collect { comics ->
                val comic = comics.find { it.id == comicId }
                comic?.let {
                    Timber.d("Data comic di halaman Detail: ${it.title}")
                    viewModel.selectComic(it)
                    binding.imgDetailCover.setImageResource(it.imageResId)
                    binding.tvDetailTitle.text = it.title
                    binding.tvDetailGenre.text = getString(R.string.label_genre) + it.genre
                    binding.tvDetailAuthor.text = getString(R.string.label_author) + it.author
                    binding.tvDetailYear.text = getString(R.string.label_year) + it.year
                    binding.tvDetailSynopsis.text = it.synopsis
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}