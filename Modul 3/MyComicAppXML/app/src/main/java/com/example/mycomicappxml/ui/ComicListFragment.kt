package com.example.mycomicappxml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycomicappxml.data.ComicDataSource
import com.example.mycomicappxml.databinding.FragmentComicListBinding

class ComicListFragment : Fragment() {

    private var _binding: FragmentComicListBinding? = null
    private val binding get() = _binding!!

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

        val highlightAdapter = ComicHighlightAdapter(ComicDataSource.getComicList(requireContext())) { comicId ->
            navigateToDetail(comicId)
        }
        binding.rvHighlight.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        binding.rvHighlight.adapter = highlightAdapter

        val comicAdapter = ComicAdapter(ComicDataSource.getComicList(requireContext())) { comicId ->
            navigateToDetail(comicId)
        }
        binding.rvComicList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvComicList.adapter = comicAdapter

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