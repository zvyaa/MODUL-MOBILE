package com.example.mycomicappxmlm4.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycomicappxmlm4.R
import com.example.mycomicappxmlm4.databinding.FragmentComicListBinding
import com.example.mycomicappxmlm4.viewmodel.ComicViewModel
import com.example.mycomicappxmlm4.viewmodel.ComicViewModelFactory
import kotlinx.coroutines.launch
import timber.log.Timber

class ComicListFragment : Fragment() {

    private var _binding: FragmentComicListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ComicViewModel by viewModels {
        ComicViewModelFactory("MyComicAppXMLM4", requireContext())
    }

    private lateinit var comicAdapter: ComicAdapter
    private lateinit var highlightAdapter: ComicHighlightAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComicListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvHighlight.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        binding.rvComicList.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.comicList.collect { comics ->
                highlightAdapter = ComicHighlightAdapter(comics) { comicId ->
                    navigateToDetail(comicId)
                }
                binding.rvHighlight.adapter = highlightAdapter

                comicAdapter = ComicAdapter(
                    comics = comics,
                    onDetailClick = { comicId ->
                        val comic = comics.find { it.id == comicId }
                        comic?.let {
                            Timber.d("Tombol Detail ditekan: ${it.title}")
                            viewModel.onDetailClicked(it)
                        }
                        navigateToDetail(comicId)
                    },
                    onExplicitIntentClick = { comic ->
                        viewModel.onExplicitIntentClicked(comic)
                    }
                )
                binding.rvComicList.adapter = comicAdapter
            }
        }

        binding.btnLanguage.setOnClickListener {
            findNavController().navigate(R.id.action_comicListFragment_to_languageFragment)
        }
    }

    private fun navigateToDetail(comicId: Int) {
        val bundle = Bundle().apply { putInt("comicId", comicId) }
        findNavController().navigate(
            R.id.action_comicListFragment_to_comicDetailFragment, bundle
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}