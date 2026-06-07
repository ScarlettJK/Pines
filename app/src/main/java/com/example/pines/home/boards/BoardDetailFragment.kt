package com.example.pines.home.boards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pines.databinding.FragmentBoardDetailBinding

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pines.home.feed.FeedAdapter
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import com.example.pines.R



class BoardDetailFragment : Fragment() {

    private var _binding: FragmentBoardDetailBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<BoardDetailViewModel>()

    private lateinit var adapter: FeedAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
            FragmentBoardDetailBinding.inflate(
                inflater,
                container,
                false
            )

        return binding.root
    }


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val boardId =
            requireArguments().getString("boardId")
                ?: return

        val boardName =
            requireArguments().getString("boardName")
                ?: ""

        binding.tvBoardName.text =
            boardName

        setupRecycler()

        observePins()

        viewModel.loadPins(boardId)
    }

    private fun setupRecycler() {

        adapter = FeedAdapter(

            onItemClick = { pin ->

                val bundle = Bundle().apply {
                    putSerializable("pin", pin)
                }

                findNavController().navigate(
                    R.id.pinDetailFragment,
                    bundle
                )
            }
        )

        binding.rvPins.layoutManager =
            StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )

        binding.rvPins.adapter = adapter
    }



    private fun observePins() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.pins.collect { pins ->

                adapter.submitList(pins)
            }
        }
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}