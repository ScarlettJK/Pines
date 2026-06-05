package com.example.pines.home.feed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pines.R
import com.example.pines.core.FragmentCommunicator
import com.example.pines.core.ResponseService
import com.example.pines.databinding.FragmentFeedBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pines.core.model.Pines

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FeedViewModel>()
    private lateinit var communicator: FragmentCommunicator
    private val adapter = FeedAdapter { pin ->
        val bundle = Bundle().apply { putParcelable("pin", pin) }
        findNavController().navigate(R.id.action_feedFragment_to_pinDetailFragment, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        communicator = requireActivity() as FragmentCommunicator

        //binding.rvPines.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPines.layoutManager =
            StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
        binding.rvPines.adapter = adapter

        observeState()
        viewModel.loadPines()
        return binding.root
    }

    fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.pinesState.collect { state ->
                    when(state){
                        is ResponseService.Loading -> {
                            communicator.manageLoader(true)
                        }
                        is ResponseService.Success -> {
                            communicator.manageLoader(false)
                            adapter.submitList(state.data)
                        }
                        is ResponseService.Error -> {
                            communicator.manageLoader(false)
                            Snackbar.make(binding.root, state.error, Snackbar.LENGTH_LONG).show()
                        }
                        null -> {}
                    }
                }
            }
        }
    }

}